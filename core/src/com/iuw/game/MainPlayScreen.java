package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

enum GameState {
    TARGET_FIRST,
    TARGET_SECOND,
    DONE,
}

public class MainPlayScreen extends ScreenAdapter {
    ArrayList<Sound> soundArray;
    PhysicalSimulation sim;
    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    Process game;
    Sprite signFrom; //fixme
    Sprite signTo;
    boolean soundIsPlaying;
    boolean Warning;
    GameState gameState;
    Timer keyWaiting;
    int pickupTargetPlanet;
    int dropTargetPlanet;
    int numDelivered;
    private float cameraScale;
    private boolean keyPressed;

    public MainPlayScreen(final Process game) {
        this.game = game;
        game.setCurrentScreen(2);
        cameraScale = 1f;
        keyPressed = false;
        soundIsPlaying = false;
        Warning = false;
        camera = new OrthographicCamera(cameraScale * Process.SCREEN_WIDTH, cameraScale * Process.SCREEN_HEIGHT);
        shapeRenderer = game.getShapeRenderer();
        soundArray = new ArrayList<>();
        sim = new PhysicalSimulation();
        numDelivered = 0;
        sim.setShipTexture(game.getTextureByName("ship"));
        sim.setSunTexture(game.getTextureByName("star"));

        signFrom = makeHereSign(game.getTextureByName("here1"));
        signTo = makeHereSign(game.getTextureByName("here3"));
        // GameSettings.getSystemVariableByName("goods")
        keyWaiting = new Timer();
        keyWaiting.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                setCameraControl(0.01f);
            }
        }, 0.01f, 0.01f);
        generatePlayfield();
    }

    Sprite makeHereSign(Texture texture) { //fixme
        Sprite arrowSign = new Sprite(texture);
        arrowSign.setOrigin(arrowSign.getWidth() / 2, 0);
        return arrowSign;
    }

    void generatePlayfield() {
        var planetNum = GameSettings.getSystemVariableByName("planets");
        createPlanets(planetNum);
        setChosenPlanets(planetNum);
        gameState = GameState.TARGET_FIRST;
    }

    void drawSprite(Sprite sprite, Vector2 position) {
        sprite.setOriginBasedPosition(position.x, position.y);
        sprite.draw(game.batch);
    }

    private void setChosenPlanets(int quantity) {
        pickupTargetPlanet = ThreadLocalRandom.current().nextInt(quantity);
        do {
            dropTargetPlanet = ThreadLocalRandom.current().nextInt(quantity);
        } while (dropTargetPlanet == pickupTargetPlanet);
    }

    @Override
    public void render(float dt) {
        setShipController();
        sim.update(dt);

        ArrayList<Vector2> trajectory = sim.ship.getPath(6, sim.fixDeltaTime, sim.SUN_POS, sim.SUN_MASS);
        Vector2 cameraPoint = new Vector2(sim.ship.apoapsis).interpolate(sim.ship.periapsis, 0.5f, Interpolation.linear);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        sim.draw(game.batch);
        switch (gameState) {
            case TARGET_FIRST:
                if (sim.isShipPlanetCollision(pickupTargetPlanet)) {
                    gameState = GameState.TARGET_SECOND;
                }

                drawSprite(signFrom, sim.planets.get(pickupTargetPlanet).position);


                break;
            case TARGET_SECOND:
                if (sim.isShipPlanetCollision(dropTargetPlanet)) {
                    gameState = GameState.DONE;
                    numDelivered++;
                }
                drawSprite(signTo, sim.planets.get(dropTargetPlanet).position);
                break;
            case DONE:
                if (numDelivered == GameSettings.getSystemVariableByName("goods")) {
                    dispose();
                    game.setScreen(game.GetScreenByIndex(4));
                } else {
                    gameState = GameState.TARGET_FIRST;
                    setChosenPlanets(GameSettings.getSystemVariableByName("planets"));
                }
                break;
        }

        game.batch.end();
        if (sim.ship.isCollide) {
            setWarningSound();
        } else Warning = false;


        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.1f, 0.7f, 0.4f, 1f);

        for (Vector2 point : trajectory) {
            shapeRenderer.circle(point.x, point.y, 2);
        }

        shapeRenderer.setColor(0.6f, 0.3f, 0.4f, 1f);
        for (PhysicalObject planet : sim.planets) {
            trajectory = planet.getPath(6, sim.fixDeltaTime, sim.SUN_POS, sim.SUN_MASS);
            for (Vector2 point : trajectory) {
                shapeRenderer.circle(point.x, point.y, 2);
            }
        }
        shapeRenderer.end();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        //todo make dispose
        soundArray.forEach(Sound::dispose);
    }

    private float getRangeFromAlpha(float alpha, float min, float max) {
        return min + alpha * max;
    }

    int ensureRange(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    /**
     * Инициализация планет
     *
     * @param quantity - число планет
     */
    private void createPlanets(int quantity) {
        assert quantity >= 2;
        assert quantity <= 8;
        var MAX_DELTA_ORBIT = 200f;
        var MIN_PLANET = 15f;
        var MAX_PLANET = 30f;

        var MAX_ORBIT = 400f + Math.random() * 100f;

        for (int i = 1; i <= quantity; i++) {
            float orbit_r = (float) (((float) i / quantity) * MAX_ORBIT + MAX_DELTA_ORBIT * Math.random());
            var angle = Math.random() * Math.PI;
            var planet_r = getRangeFromAlpha(MIN_PLANET, MAX_PLANET, (float) Math.random());
            var pos = new Vector2((float) (Math.cos(angle) * orbit_r), (float) (Math.sin(angle) * orbit_r));

            var eccentricity = (float) ((1f - 0.5f * (Math.random() + Math.random()) / 2));
            var angSpeed = (float) (90d + Math.random() * Math.random() * 150d);
            sim.createPlanet((int) planet_r, angSpeed, pos, game.getTextureByName("planet" + i), eccentricity);
        }
    }

    /**
     * Определяет охватываемую площадь и положение камеры
     *
     * @param deltaTime - время изменение кадра
     */
    private void setCameraControl(float deltaTime) {
        if (!keyPressed) {
            if (Gdx.input.isKeyPressed(Input.Keys.Q) && cameraScale > 0.4f) {
                cameraScale -= 0.05f;
                camera.zoom = cameraScale;
                keyPressed = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.E) && cameraScale < 1.95f) {
                cameraScale += 0.05f;
                camera.zoom = cameraScale;
                keyPressed = true;
            }


            if (cameraScale < 1.8f) {
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    if (Gdx.input.isKeyPressed(Input.Keys.W) && camera.position.y < Process.SCREEN_HEIGHT / 2f) {
                        camera.position.set(camera.position.x, camera.position.y + 10f, 0f);
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.S) && camera.position.y > -Process.SCREEN_HEIGHT / 2f) {
                        camera.position.set(camera.position.x, camera.position.y - 10f, 0f);
                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.A) && camera.position.x > -Process.SCREEN_WIDTH / 2f) {
                        camera.position.set(camera.position.x - 10f, camera.position.y, 0f);

                    }
                    if (Gdx.input.isKeyPressed(Input.Keys.D) && camera.position.x < Process.SCREEN_WIDTH / 2f) {
                        camera.position.set(camera.position.x + 10f, camera.position.y, 0f);
                    }
                }
            } else if (cameraScale < 2f && (Math.abs(camera.position.x) > 10f || Math.abs(camera.position.y) > 10f)) {
                float temp = (float) Math.sqrt(camera.position.y * camera.position.y + camera.position.x * camera.position.x);
                float cos = camera.position.x / temp, sin = camera.position.y / temp;
                camera.position.set(
                        camera.position.x - cos * GameSettings.DEFAULT_CAMERA_SPEED * deltaTime,
                        camera.position.y - sin * GameSettings.DEFAULT_CAMERA_SPEED * deltaTime,
                        0f);
            }


        } else if (!Gdx.input.isKeyPressed(Input.Keys.Q) || !Gdx.input.isKeyPressed(Input.Keys.E)) {
            keyPressed = false;
        }
    }

    private void setShipController() {
        float thrust = 0f;
        float steering = 0f;
        boolean pressed = false;
        if (!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                thrust = 50f;
                pressed = true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                thrust = -50f;
                pressed = true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                steering = -1f;
                pressed = true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                steering = 1f;
                pressed = true;
            }
            if (pressed) setSoundTimerTaskByName("ship");
            sim.setInput(steering, thrust);
        }
    }

    private void setSoundTimerTaskByName(String name) {
        if (!soundIsPlaying) {
            Sound sound = game.getSoundByName(name);
            sound.play(GameSettings.getVolumeLevelByName("sound"));

            soundArray.add(sound);
            soundIsPlaying = true;
            Timer.Task waiter;
            waiter = new Timer.Task() {
                @Override
                public void run() {
                    sound.dispose();
                    soundIsPlaying = false;
                    soundArray.remove(sound);

                }
            };
            keyWaiting.scheduleTask(waiter, 5f);
        }
    }

    private void stopAllSounds() {
        soundArray.forEach(Sound::stop);
        soundArray.forEach(Sound::dispose);
    }

    private void setWarningSound() {
        if (!Warning) {
            stopAllSounds();
            Warning = true;
            soundIsPlaying = false;
            setSoundTimerTaskByName("collision");
        }
    }
}

class PhysicalObject {
    static final float BIG_G = 1f; // IRL 6.67f * 1e-11f;
    public Vector2 position;
    public Vector2 velocity;
    public Vector2 force;
    public float angle;
    public float angularSpeed;
    public float mass;
    public float radius;

    public ArrayList<Vector2> curPath;
    public Vector2 apoapsis;
    public Vector2 periapsis;
    boolean pathReqUpdate;
    boolean isCollide;
    Sprite sprite;

    public PhysicalObject(PhysicalObject other) {
        this.position = new Vector2(other.position);
        this.velocity = new Vector2(other.velocity);
        this.force = new Vector2(other.force);
        this.angle = other.angle;
        this.angularSpeed = other.angularSpeed;
        this.mass = other.mass;
        pathReqUpdate = true;
        isCollide = false;
        // this.sprite = other.sprite; // We are cloning object for headless simulation, so we don't need to copy sprite
    }

    public PhysicalObject(float x, float y, float speedX, float speedY, float mass) {
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(speedX, speedY);
        this.mass = mass;

        this.force = new Vector2();
        angle = 0;
        angularSpeed = 0;
        pathReqUpdate = true;
        isCollide = false;
    }

    static Vector2 getGravitationForce(float m1, float m2, Vector2 r1, Vector2 r2) {
        Vector2 vec = new Vector2(r2).sub(r1);
        float magnitude = BIG_G * m1 * m2 / vec.len2();
        return vec.setLength(magnitude);
    }

    public void setTexture(Texture texture) {
        this.sprite = new Sprite(texture);
        this.sprite.setOriginCenter();
    }

    // todo rewrite this probably
    public ArrayList<Vector2> getPath(float spacing, float deltaTime, Vector2 sunPos, float sunMass) {
        if (!pathReqUpdate) {
            return curPath;
        }
        isCollide = false;
        final float posEps = 5f;
        final int MAX_STEPS = 500;
        final int SUN_SIZE = 64 - 30; // fixme Put in real sun size

        spacing = spacing * spacing;

        ArrayList<Vector2> trajectory = new ArrayList<>();
        PhysicalObject clonedShip = new PhysicalObject(this);
        Vector2 startPos = new Vector2(clonedShip.position);

        int i = 0;
        boolean inStart = true;
        Vector2 prevPos = new Vector2(clonedShip.position);

        trajectory.add(new Vector2(clonedShip.position));

        float closeDistance = Float.POSITIVE_INFINITY;
        float farDistance = Float.NEGATIVE_INFINITY;

        while (i < MAX_STEPS) {
            clonedShip.applyGravity(sunPos, sunMass);
            clonedShip.update(deltaTime);

            if (prevPos.dst(clonedShip.position) > spacing) {
                prevPos = new Vector2(clonedShip.position);
                trajectory.add(new Vector2(clonedShip.position));
                i++;
            }

            float a = startPos.dst2(clonedShip.position); //fixme refactor this

            float distToSun = clonedShip.position.dst2(sunPos);

            if (distToSun < closeDistance) {
                closeDistance = distToSun;
                periapsis = clonedShip.position.cpy();
            }
            if (distToSun > farDistance) {
                farDistance = distToSun;
                apoapsis = clonedShip.position.cpy();

                /* System.out.println("FAR");
                System.out.println(farDistance);
                 System.out.println(apoapsis);*/
            }
            if (distToSun < SUN_SIZE * SUN_SIZE) { // If moving throughout a sun
                isCollide = true;
                break; // Don't draw buggy orbit
            } else {
                if (a > 2 * posEps) {
                    inStart = false;
                }
            }

            if (a < posEps && !inStart) {
                break;
            } else {
                if (a > 2 * posEps) {
                    inStart = false;
                }
            }


        }

        clonedShip.position.setZero();
        curPath = trajectory;
        pathReqUpdate = false;
        return trajectory;
    }

    public boolean collidesWith(PhysicalObject other) {
        float distance = this.radius + other.radius;
        return this.position.dst2(other.position) < distance * distance;
    }

    public void setSize(int sizeX, int sizeY) {
        this.radius = sizeX / 2f; //fixme dirty hack
        this.sprite.setSize(sizeX, sizeY);
        this.sprite.setOriginCenter();
        this.sprite.setCenter(0, 0);
    }

    public void applyForce(@NotNull Vector2 force) {
        this.force.add(force);
        pathReqUpdate = true;
    }

    public void applyForceDirected(float angle, float force) {
        if (force == 0) {
            return;
        }
        this.force.add(new Vector2(0, force).rotateDeg(angle));
        pathReqUpdate = true;
    }

    public void applyAngularAcceleration(float acceleration, float dt) {
        angularSpeed += -acceleration * dt;
    }

    public void applyGravity(Vector2 position, float mass) {
        force.add(getGravitationForce(this.mass, mass, this.position, position));
    }

    public void makeOrbit(float mass, Vector2 center, boolean clockwise, float squishification) {
        Vector2 r = new Vector2(center).sub(position);
        if (clockwise) {
            velocity = r.rotate90(1).setLength2(BIG_G * mass / r.len() * squishification);
        } else {
            velocity = r.rotate90(-1).setLength2(BIG_G * mass / r.len() * squishification);
        }
    }

    public void update(float deltaTime) {
        // Apply force
        velocity.mulAdd(force, deltaTime / mass);
        force.setZero();

        // Update position
        position.mulAdd(velocity, deltaTime);
        angle += angularSpeed * deltaTime;
    }

    public void draw(SpriteBatch batch) {
        sprite.setOriginBasedPosition(position.x, position.y);
        sprite.setRotation(angle);
        sprite.draw(batch);
    }
}

class PhysicalSimulation {
    final int STEPS = 10;
    final float fixDeltaTime;
    final float SUN_MASS = 1e7f;
    final Vector2 SUN_POS = new Vector2(0f, 0f);
    final float ENGINE_FORCE = 5f;
    final float TURN_FORCE = 7000f;
    float steering;
    float thrust;

    int simSpeedFactor;
    float timeLeftover = 0;

    PhysicalObject ship;
    PhysicalObject sun;
    ArrayList<PhysicalObject> planets;

    public PhysicalSimulation() {
        simSpeedFactor = 1;
        timeLeftover = 0;
        fixDeltaTime = 1f / 60f / STEPS;

        ship = new PhysicalObject(200, 0, 0, 1, 1);
        ship.makeOrbit(SUN_MASS, SUN_POS, true, 1f);

        sun = new PhysicalObject(0, 0, 0, 0, 100);
        sun.applyAngularAcceleration(-10f, 1f);

        planets = new ArrayList<>();
    }

    public void createPlanet(int planetRadius, float planetRotation, Vector2 pos, Texture texture, float squishification) {
        PhysicalObject planet = new PhysicalObject(pos.x, pos.y, 0, 0, 1f);
        planet.makeOrbit(SUN_MASS, SUN_POS, true, squishification);

        planet.applyAngularAcceleration(planetRotation, 1f);

        planet.setTexture(texture);
        planet.setSize(planetRadius * 2, planetRadius * 2);

        planets.add(planet);
    }

    public void setShipTexture(Texture texture) {
        ship.setTexture(texture);
        ship.setSize(30, 30);
    }

    public void setSunTexture(Texture texture) {
        sun.setTexture(texture);
        sun.setSize(64, 64);
    }

    public void update(float deltaTime) {
        deltaTime = (deltaTime + timeLeftover) * simSpeedFactor;
        int steps = (int) (deltaTime / fixDeltaTime);
        timeLeftover = deltaTime - steps * fixDeltaTime;

        for (int i = 0; i < steps; i++) {
            ship.applyForceDirected(ship.angle, thrust * ENGINE_FORCE);
            ship.applyAngularAcceleration(steering * TURN_FORCE, fixDeltaTime);
            ship.applyGravity(SUN_POS, SUN_MASS);

            ship.update(fixDeltaTime);
            ship.angularSpeed *= 0.9;
            for (PhysicalObject planet : planets) {
                planet.applyGravity(SUN_POS, SUN_MASS);
                planet.update(fixDeltaTime);
            }
            sun.update(fixDeltaTime);
        }
    }

    public boolean isShipPlanetCollision(int planetId) {
        var planet = planets.get(planetId);
        return planet.collidesWith(ship);
    }

    public void draw(SpriteBatch batch) {
        ship.draw(batch);
        sun.draw(batch);
        for (PhysicalObject planet : planets) {
            planet.draw(batch);
        }
    }

    public void setInput(float steering, float thrust) {
        this.steering = steering;
        this.thrust = thrust;
    }
}
package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

enum GameState {
    TARGET_FIRST,
    TARGET_SECOND,
    DONE,
}

public class MainPlayScreen extends ScreenAdapter {
    PhysicalSimulation sim;
    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    Process game;
    private float cameraScale;
    private boolean keyPressed;

    Sprite here1Sign; //fixme
    Sprite here2Sign;
    Sprite here3Sign;

    GameState gameState;
    GameSound sound;

    int pickupTargetPlanet;
    int dropTargetPlanet;

    public MainPlayScreen(final Process game) {
        this.game = game;
        sound = new GameSound();

        game.setCurrentScreen(2);
        cameraScale = 1f;
        keyPressed = false;
        camera = new OrthographicCamera(cameraScale * Process.SCREEN_WIDTH, cameraScale * Process.SCREEN_HEIGHT);
        shapeRenderer = game.getShapeRenderer();

        sim = new PhysicalSimulation();
        sound = new GameSound();

        sim.setShipTexture(game.getTextureByName("ship"));
        sim.setSunTexture(game.getTextureByName("star"));

        here1Sign = makeHereSign(game.getTextureByName("here1"));
        here2Sign = makeHereSign(game.getTextureByName("here2"));
        here3Sign = makeHereSign(game.getTextureByName("here3"));

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
        pickupTargetPlanet = ThreadLocalRandom.current().nextInt(planetNum);
        do {
            dropTargetPlanet = ThreadLocalRandom.current().nextInt(planetNum);
        } while (dropTargetPlanet == pickupTargetPlanet);

        gameState = GameState.TARGET_FIRST;
        sound.ambienceStart();
    }

    void drawSprite(Sprite sprite, Vector2 position) {
        sprite.setOriginBasedPosition(position.x, position.y);
        sprite.draw(game.batch);
    }

    @Override
    public void render(float dt) {
        setShipController();
        sim.update(dt);

        // Ship path
        ArrayList<Vector2> trajectory = sim.ship.getPath(6, sim.fixDeltaTime, sim.SUN_POS, sim.SUN_MASS);
        Vector2 cameraPoint = new Vector2(sim.ship.apoapsis).interpolate(sim.ship.periapsis, 0.5f, Interpolation.linear);

        //camera.position.set(, 0f);
        camera.zoom = 2.0f;
        camera.update();

        //setCameraControl(dt);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        sim.draw(game.batch);

        switch (gameState) {
            case TARGET_FIRST:
                if (sim.isShipPlanetCollision(pickupTargetPlanet)) {
                    sound.done();
                    gameState = GameState.TARGET_SECOND;
                }

                drawSprite(here1Sign, sim.planets.get(pickupTargetPlanet).position);
                drawSprite(here2Sign, sim.planets.get(dropTargetPlanet).position);
                break;
            case TARGET_SECOND:
                if (sim.isShipPlanetCollision(dropTargetPlanet)) {
                    sound.done();
                    gameState = GameState.DONE;
                }
                drawSprite(here3Sign, sim.planets.get(dropTargetPlanet).position);
                break;
            case DONE:
                game.setScreen(game.getNextScreen(4));
                sound.ambienceStop();
                break;
        }

        game.batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.1f, 0.7f, 0.4f, 1f);


        for (Vector2 point : trajectory) {
            shapeRenderer.circle(point.x, point.y, 2);
        }

        shapeRenderer.setColor(0.6f, 0.3f, 0.4f, 1f);
        for (PhysicalObject planet : sim.planets) {
            trajectory = planet.getPath(6, sim.fixDeltaTime, sim.SUN_POS, sim.SUN_MASS);
            for (int i = 0; i < trajectory.size(); i++) {
                Vector2 point = trajectory.get(i);
                shapeRenderer.circle(point.x, point.y, 2);
            }
        }
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        sound.dispose();
        //todo make dispose
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
            sim.createPlanet((int) planet_r, angSpeed, pos, game.getTextureByName("planet" + String.valueOf(i)), eccentricity);
        }
    }

    /**
     * Определяет охватываемую площадь и положение камеры
     *
     * @param deltaTime - время изменение кадра
     */
    private void setCameraControl(float deltaTime) {
        if (!keyPressed) {
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_1) && cameraScale > 0.4f) {
                cameraScale -= 0.05;
                camera.zoom = cameraScale;
                keyPressed = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_2) && cameraScale < 1.95f) {
                cameraScale += 0.05;
                keyPressed = true;
                camera.zoom = cameraScale;
            }

            /*
            if (cameraScale < 1.8f) {
                if (Gdx.input.isKeyPressed(Input.Keys.UP) && camera.position.y < Process.SCREEN_HEIGHT / 2f) {
                    camera.position.set(camera.position.x, camera.position.y + 10f, 0f);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && camera.position.y > -Process.SCREEN_HEIGHT / 2f) {
                    camera.position.set(camera.position.x, camera.position.y - 10f, 0f);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && camera.position.x > -Process.SCREEN_WIDTH / 2f) {
                    camera.position.set(camera.position.x - 10f, camera.position.y, 0f);

                }
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && camera.position.x < Process.SCREEN_WIDTH / 2f) {
                    camera.position.set(camera.position.x + 10f, camera.position.y, 0f);
                }
            } else if (cameraScale < 2f && (Math.abs(camera.position.x) > 10f || Math.abs(camera.position.y) > 10f)) {
                float temp = (float) Math.sqrt(camera.position.y * camera.position.y + camera.position.x * camera.position.x);
                float cos = camera.position.x / temp, sin = camera.position.y / temp;
                camera.position.set(
                        camera.position.x - cos * GameSettings.DEFAULT_CAMERA_SPEED * deltaTime,
                        camera.position.y - sin * GameSettings.DEFAULT_CAMERA_SPEED * deltaTime,
                        0f);
            } */
        } else if (!Gdx.input.isKeyPressed(Input.Keys.NUM_1) || !Gdx.input.isKeyPressed(Input.Keys.NUM_2))
            keyPressed = false;
    }

    private void setShipController() {
        float thrust = 0f;
        float steering = 0f;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            thrust = +50;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            thrust = -50;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            steering = -1f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            steering = 1f;
        }

        if (thrust != 0f){
            sound.engineStart();
        } else {
            sound.engineStop();
        }

        if (steering != 0f){
            sound.turnStart();
        } else {
            sound.turnStop();
        }

        sim.setInput(steering, thrust);
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

    Sprite sprite;

    public PhysicalObject(PhysicalObject other) {
        this.position = new Vector2(other.position);
        this.velocity = new Vector2(other.velocity);
        this.force = new Vector2(other.force);
        this.angle = other.angle;
        this.angularSpeed = other.angularSpeed;
        this.mass = other.mass;
        pathReqUpdate = true;
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
                System.out.println(apoapsis); */
            }

            if (distToSun < SUN_SIZE * SUN_SIZE) { // If moving throughout a sun
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
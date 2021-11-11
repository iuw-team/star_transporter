package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainPlayScreen extends ScreenAdapter {
    private final float delayTime = 0.3f;
    Texture shipTexture;
    Texture sunTexture;
    Texture planetTexture;
    Texture asteroidTexture;
    PhysicalSimulation sim;
    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    Process game;
    private float cameraScale;
    private boolean keyPressed;

    public MainPlayScreen(final Process game) {
        this.game = game;
        game.setCurrentScreen(2);
        cameraScale = 1f;
        keyPressed = false;
        camera = new OrthographicCamera(cameraScale * Process.SCREEN_WIDTH, cameraScale * Process.SCREEN_HEIGHT);
        shapeRenderer = game.getShapeRenderer();
        shipTexture = game.getTextureByName("ship");
        sunTexture = game.getTextureByName("star");
        planetTexture = game.getTextureByName("planet");
        asteroidTexture = game.getTextureByName("asteroid");
        sim = new PhysicalSimulation();

        sim.setShipTexture(shipTexture);
        sim.setSunTexture(sunTexture);
        createPlanets(GameSettings.getSystemVariableByName("planets"));
    }


    @Override
    public void render(float dt) {
        setShipController();
        sim.update(dt);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        sim.draw(game.batch);
        game.batch.end();
        setCameraControl(dt);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.1f, 0.7f, 0.4f, 1f);
        //массив из координант которых рисуем кружки
        ArrayList<Vector2> trajectory = sim.ship.getPath(6, dt, sim.SUN_POS, sim.SUN_MASS);

        for (Vector2 point : trajectory) {
            shapeRenderer.circle(point.x, point.y, 2);
        }

        shapeRenderer.setColor(0.6f, 0.3f, 0.4f, 1f);
        for (PhysicalObject planet : sim.planets) {
            trajectory = planet.getPath(6, dt, sim.SUN_POS, sim.SUN_MASS);
            for (Vector2 point : trajectory) {
                shapeRenderer.circle(point.x, point.y, 2);
            }
        }
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        //todo make dispose
    }

    /**
     * Инициализация планет
     *
     * @param quantity - число планет
     */
    private void createPlanets(int quantity) {
        float mass = 100f;
        int radius = 10;
        int partX = 1, partY = 1;
        Vector2 position = new Vector2(-75, 75);
        for (int i = 1; i <= quantity; i++) {
            sim.createPlanet(radius, mass, position, new Vector2(), planetTexture);
            radius *= MathUtils.random(1.2f, 1.4f);
            mass *= MathUtils.random(1.4f, 1.7f);
            if (MathUtils.random(1, 2) == 1) partX = -partX;
            if (MathUtils.random(1, 2) == 1) partY = -partY;
            position.x = partX * position.x * 1.3f;
            position.y = partX * position.y * 1.3f;
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
            }
            camera.update();
        } else if (!Gdx.input.isKeyPressed(Input.Keys.NUM_1) || !Gdx.input.isKeyPressed(Input.Keys.NUM_2))
            keyPressed = false;
    }

    /**
     * Настро
     */
    private void setShipController() {
        float deltaTime = 0.01f;
        if (GameSettings.soundIsPlaying) {
            GameSettings.elapsedTime += delayTime;
            if (GameSettings.elapsedTime > GameSettings.playTime) GameSettings.soundIsPlaying = false;
        } else {
            float thrust = 0f;
            float steering;
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                thrust += 50;
            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                thrust -= 50;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                steering = -1f;
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                steering = 1f;
            } else {
                steering = 0f;
            }
            if (thrust != 0f) {
                System.out.println(true);
                game.getSoundByName("ship").play(GameSettings.getVolumeLevelByName("sound"));
                GameSettings.soundIsPlaying = true;
            }
            sim.setInput(steering, thrust);
        }
    }
}

class PhysicalObject {
    //final float BIG_G = 6.67f * 1e-11f;
    static final float BIG_G = 1f;
    public Vector2 position;
    public Vector2 velocity;
    public Vector2 force;
    public float angle;
    public float angularSpeed;
    public float mass;
    public float radius;

    public ArrayList<Vector2> curPath;
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
        //this.sprite = other.sprite;
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
        this.sprite.setCenter(0, 0);
    }

    // fixme fix this whole method, it's very buggy
    public ArrayList<Vector2> getPath(float spacing, float deltaTime, Vector2 sunPos, float sunMass) {
        if (!pathReqUpdate) {
            return curPath;
        }

        float simDeltaTime = 1f / 600f; //fixme
        deltaTime *= 1f / 600f;
        final float posEps = 0.5f;
        final int MAX_STEPS = 1000;
        //расстояние отлёта
        spacing = spacing * spacing;

        ArrayList<Vector2> trajectory = new ArrayList<>();
        PhysicalObject clonedShip = new PhysicalObject(this);
        Vector2 startPos = new Vector2(clonedShip.position);

        float cumDt = 0;
        int i = 0;
        boolean inStart = true;
        Vector2 prevPos = new Vector2(clonedShip.position);

        trajectory.add(new Vector2(clonedShip.position));
        while (i < MAX_STEPS) {
            clonedShip.applyGravity(sunPos, sunMass);
            clonedShip.update(deltaTime);

            if (prevPos.dst(clonedShip.position) > spacing) {
                prevPos = new Vector2(clonedShip.position);
                trajectory.add(new Vector2(clonedShip.position));
                i++;
            }
            float a = startPos.dst2(clonedShip.position); //fixme

            if (clonedShip.position.len2() < 64) { //fixme
                break;
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

    public void makeRoundOrbit(float mass, Vector2 center, boolean clockwise) { //fixme for any orbit position
        Vector2 r = new Vector2(center).sub(position);
        if (clockwise) {
            velocity = r.rotate90(1).setLength2(BIG_G * mass / r.len());
        } else {
            velocity = r.rotate90(-1).setLength2(BIG_G * mass / r.len());
        }
    }

    public void update(float deltaTime) {
        //изменяем скорость
        velocity.mulAdd(force, deltaTime / mass);
        //обнуляем ситу тяжести
        force.setZero();
        //нынешнее положение
        position.mulAdd(velocity, deltaTime);
        angle += angularSpeed * deltaTime;
    }

    public void draw(SpriteBatch batch) {
        sprite.setCenter(position.x, position.y);
        sprite.setRotation(angle);
        sprite.draw(batch);
    }
}

class PhysicalSimulation {
    final int STEPS = 10;
    final float SUN_MASS = 1e7f;
    final Vector2 SUN_POS = new Vector2(0f, 0f);
    final float ENGINE_FORCE = 5f;
    final float TURN_FORCE = 90f;
    float steering;
    float thrust;
    int simSpeedFactor;

    PhysicalObject ship;
    PhysicalObject sun;
    Array<PhysicalObject> asteroids;
    ArrayList<PhysicalObject> planets;

    public PhysicalSimulation() {
        simSpeedFactor = 1;
        ship = new PhysicalObject(100, 0, 0, 1, 1);
        ship.makeRoundOrbit(SUN_MASS, SUN_POS, true);
        sun = new PhysicalObject(0, 0, 0, 0, 100);
          sun.applyAngularAcceleration(-0f, 1f);
        planets = new ArrayList<>();
        asteroids = new Array<>();
    }
    public void createAsteroid(int asteroidRadius, float asteroidRotation, Vector2 position, Vector2 orbitDisturb, Texture texture){
//     PhysicalObject asteroid = new PhysicalObject((position.x, position.y, 0, 1f);
//     asteroid.applyAngularAcceleration(asteroidRotation, 1f);
//     asteroid.setTexture(texture);
//        asteroid.setSize(asteroidRadius * 2, asteroidRadius * 2);

    }
    public void createPlanet(int planetRadius, float planetRotation, Vector2 pos, Vector2 orbitDisturb, Texture texture) {
        PhysicalObject planet = new PhysicalObject(pos.x, pos.y, 0, 0, 1f);
        planet.makeRoundOrbit(SUN_MASS, SUN_POS, true);

        planet.applyForce(orbitDisturb);
        planet.applyAngularAcceleration(planetRotation, 1f);

        planet.setTexture(texture);
        planet.setSize(planetRadius * 2, planetRadius * 2);

        planets.add(planet);
    }

    public void setShipTexture(Texture texture) {
        ship.setTexture(texture);
        ship.setSize(20, 20);
    }

    public void setSunTexture(Texture texture) {
        sun.setTexture(texture);
        sun.setSize(64, 64);
    }

    public void update(float deltaTime) {

        //final float MAX_DT = 1f/30f;
        //  deltaTime = Math.min(deltaTime, MAX_DT);

        deltaTime = 1f / 60f;
        deltaTime = deltaTime / STEPS;
        for (int i = 0; i < STEPS * simSpeedFactor; i++) {
            ship.applyForceDirected(ship.angle, thrust * ENGINE_FORCE);

            ship.applyAngularAcceleration(steering * TURN_FORCE, deltaTime);
            ship.applyGravity(SUN_POS, SUN_MASS);

            ship.update(deltaTime);
            for (PhysicalObject planet : planets) {
                planet.applyGravity(SUN_POS, SUN_MASS);
                planet.update(deltaTime);
            }
            for (PhysicalObject asteroid : asteroids) {
                //asteroid.applyGravity(SUN_POS, SUN_MASS);
                asteroid.update(deltaTime);
            }
            sun.update(deltaTime);
        }

        if (sun.collidesWith(ship)) {
            System.exit(1);
        }
    }

    public void draw(SpriteBatch batch) {
        ship.draw(batch);
        sun.draw(batch);
        for (PhysicalObject asteroid : planets) {
            asteroid.draw(batch);
        }
        for (PhysicalObject planet : planets) {
            planet.draw(batch);
        }
    }

    public void setInput(float steering, float thrust) {
        this.steering = steering;
        this.thrust = thrust;
    }
}
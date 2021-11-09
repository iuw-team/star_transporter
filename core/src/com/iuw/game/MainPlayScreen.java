package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainPlayScreen extends ScreenAdapter {
    Texture shipTexture;
    Texture sunTexture;
    Texture planetTexture;

    PhysicalSimulation sim;
    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    Process game;

    public MainPlayScreen(final Process game) {
        this.game = game;
        camera = new OrthographicCamera(600 * 2, 400 * 2);
        shapeRenderer = new ShapeRenderer();

        shipTexture = new Texture("pixel_ship.png");
        sunTexture = new Texture("sun.png");
        planetTexture = new Texture("pixel_planet.png");

        sim = new PhysicalSimulation();

        sim.setShipTexture(shipTexture);
        sim.setSunTexture(sunTexture);

        //todo planet generation
        sim.createPlanet(20, 100, new Vector2(-100, 100), new Vector2(), planetTexture);
        sim.createPlanet(10, 100, new Vector2(-200, -200), new Vector2(), planetTexture);
        sim.createPlanet(30, 200, new Vector2(-0, -200), new Vector2(), planetTexture);
    }

    @Override
    public void render(float dt) {
        float thrust;
        float steering;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            thrust = 1f;
        } else {
            thrust = 0f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            steering = -1f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            steering = 1f;
        } else {
            steering = 0f;
        }

        sim.setInput(steering, thrust);
        sim.update(dt);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        sim.draw(game.batch);
        game.batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.1f, 0.7f, 0.4f, 1f);
        ArrayList<Vector2> trajectory = sim.ship.getPath(6, sim.SUN_POS, sim.SUN_MASS);

        for (Vector2 point : trajectory) {
            shapeRenderer.circle(point.x, point.y, 2);
        }

        shapeRenderer.setColor(0.6f, 0.3f, 0.4f, 1f);
        for (PhysicalObject planet : sim.planets) {
            trajectory = planet.getPath(6, sim.SUN_POS, sim.SUN_MASS);
            for (int i = 0; i < trajectory.size(); i++) {
                Vector2 point = trajectory.get(i);
                shapeRenderer.circle(point.x, point.y, 2);
            }
        }
        shapeRenderer.end();
    }


    @Override
    public void dispose() {
        //todo make dispose
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

    static Vector2 getGravitationForce(float m1, float m2, Vector2 r1, Vector2 r2) {
        Vector2 vec = new Vector2(r2).sub(r1);
        float magnitude = BIG_G * m1 * m2 / vec.len2();
        return vec.setLength(magnitude);
    }

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

    public void setTexture(Texture texture) {
        this.sprite = new Sprite(texture);
        this.sprite.setOriginCenter();
        this.sprite.setCenter(0, 0);
    }

    // fixme fix this whole method, it's very buggy
    public ArrayList<Vector2> getPath(float spacing, Vector2 sunPos, float sunMass) {
        if (!pathReqUpdate) {
            return curPath;
        }

        float simDt = 1f / 600f; //fixme
        final float posEps = 0.5f;
        final int MAX_STEPS = 1000;

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
            clonedShip.update(simDt);

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

    public boolean collidesWith(PhysicalObject other){
        float rSum = this.radius + other.radius;
        return this.position.dst2(other.position) < rSum*rSum;
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

    public void update(float dt) {
        velocity.mulAdd(force, dt / mass);
        force.setZero();

        position.mulAdd(velocity, dt);
        angle += angularSpeed * dt;
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

    float steering;
    float thrust;
    final float ENGINE_FORCE = 5f;
    final float TURN_FORCE = 90f;
    int simSpeedFactor;

    PhysicalObject ship;
    PhysicalObject sun;
    ArrayList<PhysicalObject> planets;

    public PhysicalSimulation() {
        simSpeedFactor = 1;
        ship = new PhysicalObject(100, 0, 0, 1, 1);
        ship.makeRoundOrbit(SUN_MASS, SUN_POS, true);

        sun = new PhysicalObject(0, 0, 0, 0, 100);
        sun.applyAngularAcceleration(-90f, 1f);

        planets = new ArrayList<>();
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

    public void update(float dt) {

        //final float MAX_DT = 1f/30f;
        //dt = Math.min(dt, MAX_DT);

        dt = 1f / 60f;
        dt = dt / STEPS;
        for (int i = 0; i < STEPS*simSpeedFactor; i++) {
            ship.applyForceDirected(ship.angle, thrust * ENGINE_FORCE);

            ship.applyAngularAcceleration(steering * TURN_FORCE, dt);
            ship.applyGravity(SUN_POS, SUN_MASS);

            ship.update(dt);
            for (PhysicalObject planet : planets) {
                planet.applyGravity(SUN_POS, SUN_MASS);
                planet.update(dt);
            }
            sun.update(dt);
        }
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
    public boolean collidedWithSun(){
       return sun.collidesWith(ship);
    }
}
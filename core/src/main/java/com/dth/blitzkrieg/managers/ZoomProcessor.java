package com.dth.blitzkrieg.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ZoomProcessor extends InputAdapter {
    private final OrthographicCamera cam;
    private final Vector2 lastTouch = new Vector2();

    // Zoom constants, they lock zoom to certain levels so it's not possible to zoom in or out too much
    private static final int MIN_ZOOM = 40;
    private static final int MAX_ZOOM = 250;

    // Changes over time, holds the current zoom factor (larger number = smaller objects)
    private float currentZoom = 100f;

    public ZoomProcessor(OrthographicCamera camera) {
	this.cam = camera;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
	if (button != Buttons.RIGHT) return false;

	Vector2 p = screenToWorld(x, y);
	lastTouch.set(p);
	return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
	// Check if right mouse button has been pressed
	if (!Gdx.input.isButtonPressed(Buttons.RIGHT)) return false;

	// Screen to world coordinates
	Vector2 p = screenToWorld(x, y);
	Vector2 delta = new Vector2(p).sub(lastTouch);

	// Move camera
	cam.translate(-delta.x, -delta.y, 0);

	// Check camera bounds
	int northBound = 300;
	int southBound = -300;
	int eastBound = 300;
	int westBound = -300;

	if (cam.position.x > eastBound) cam.position.x = eastBound;
	if (cam.position.x < westBound) cam.position.x = westBound;
	if (cam.position.y > northBound) cam.position.y = northBound;
	if (cam.position.y < southBound) cam.position.y = southBound;

	cam.update();

	lastTouch.set(screenToWorld(x, y));
	return false;
    }

    @Override
    public boolean scrolled(int amount) {
	currentZoom *= (amount < 0) ? 1.10f : 0.90f;
	currentZoom = Math.clamp(currentZoom, MIN_ZOOM, MAX_ZOOM);

	cam.zoom = 100f / currentZoom;
	cam.update();
	return false;
    }

    private Vector2 screenToWorld(int x, int y) {
	Vector3 v3 = new Vector3(x, y, 0);
	cam.unproject(v3);
	return new Vector2(v3.x, v3.y);
    }
}
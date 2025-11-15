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

    private final int[] zoomLevels = {41, 51, 64, 80, 100, 125, 156, 195, 244};
    private int zoomLevel = 100;

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
	if (amount > 0 && zoomLevel > zoomLevels[0]) {
	    for (int i = 0; i < zoomLevels.length; i++) {
		if (zoomLevels[i] == zoomLevel) {
		    zoomLevel = zoomLevels[i - 1];
		    break;
		}
	    }
	}

	if (amount < 0 && zoomLevel < zoomLevels[zoomLevels.length - 1]) {
	    for (int i = 0; i < zoomLevels.length; i++) {
		if (zoomLevels[i] == zoomLevel) {
		    zoomLevel = zoomLevels[i + 1];
		    break;
		}
	    }
	}

	cam.zoom = 100f / zoomLevel;
	cam.update();
	return false;
    }

    private Vector2 screenToWorld(int x, int y) {
	Vector3 v3 = new Vector3(x, y, 0);
	cam.unproject(v3);
	return new Vector2(v3.x, v3.y);
    }
}
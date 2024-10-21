package rio.com;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class LineDraggingGame extends ApplicationAdapter implements InputProcessor {
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;

    // First line: start and end points
    private Vector2 startPoint1;
    private Vector2 endPoint1;
    private boolean isDragging1 = false;

    // Second line: start and end points
    private Vector2 startPoint2;
    private Vector2 endPoint2;
    private boolean isDragging2 = false;

    @Override
    public void create() {
        // Initialize ShapeRenderer for drawing the lines
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Set initial start points for both lines
        startPoint1 = new Vector2(Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() / 2f); // First point
        endPoint1 = new Vector2(startPoint1); // Initially the same as start

        startPoint2 = new Vector2(2 * Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() / 2f); // Second point
        endPoint2 = new Vector2(startPoint2); // Initially the same as start

        // Set input processor to handle mouse input
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        // Clear the screen
        ScreenUtils.clear(0.6f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update camera
        camera.update();

        // Set line thickness using OpenGL
        Gdx.gl.glLineWidth(10); // Set line thickness to 10

        // Begin ShapeRenderer drawing in line mode
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Draw the first line if dragging
        if (isDragging1) {
            shapeRenderer.line(startPoint1.x, startPoint1.y, endPoint1.x, endPoint1.y);
        }

        // Draw the second line if dragging
        if (isDragging2) {
            shapeRenderer.line(startPoint2.x, startPoint2.y, endPoint2.x, endPoint2.y);
        }

        shapeRenderer.end();

        // Reset the line width back to 1 for other renderings
        Gdx.gl.glLineWidth(1);
    }

    @Override
    public void dispose() {
        // Clean up resources
        shapeRenderer.dispose();
    }

    // InputProcessor methods

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == 0) {
            // Convert screen coordinates to world coordinates
            Vector2 mousePos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY); // Invert Y axis

            // Start dragging both lines
            isDragging1 = true;
            isDragging2 = true;

            // Update the end points to where the mouse is clicked
            endPoint1.set(mousePos);
            endPoint2.set(mousePos);
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 mousePos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY); // Invert Y axis

        // If dragging the first line, update its end point
        if (isDragging1) {
            endPoint1.set(mousePos);
        }

        // If dragging the second line, update its end point
        if (isDragging2) {
            endPoint2.set(mousePos);
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Stop dragging the first or second line when the left mouse button is released
        if (button == 0) {
            isDragging1 = false;
            isDragging2 = false;
        }
        return true;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    // Unused InputProcessor methods (but they must be implemented)

    @Override
    public boolean keyDown(int keycode) { return false; }

    @Override
    public boolean keyUp(int keycode) { return false; }

    @Override
    public boolean keyTyped(char character) { return false; }

    @Override
    public boolean mouseMoved(int screenX, int screenY) { return false; }

    @Override
    public boolean scrolled(float amountX, float amountY) { return false; }
}

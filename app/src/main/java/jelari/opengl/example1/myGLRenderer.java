package jelari.opengl.example1;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES10.glClear;
import static android.opengl.GLES10.glDrawArrays;
import static javax.microedition.khronos.opengles.GL10.GL_COLOR_BUFFER_BIT;
import static javax.microedition.khronos.opengles.GL10.GL_LINES;
import static javax.microedition.khronos.opengles.GL10.GL_POINTS;
import static javax.microedition.khronos.opengles.GL10.GL_TRIANGLES;

/**
 * Created by jelari on 18/4/16.
 */

public class myGLRenderer implements GLSurfaceView.Renderer {

    private float i=0.0f;
    private float j=0.0f;
    private float k= 0.0f;

    private  int mProgram;
    private FloatBuffer nativeBufferVertices;
    private ShortBuffer nativedrawingListBuffer;  // used in native heap memory.

    private short drawingOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices
    private int mUColorLocation ;



    private int mPositionHandler;

    // This Array is not in the Native Memory, to get this into native memory we need to allocate
    // bytebuffer which is allocated directly into the native memory.

    float[] vertices = {

            -1f, 0.0f,  // Vertical and Horizontal.
            0.0f, 0.0f,
            1.0f, 1.0f,

            // Triangle 2
            1.0f, -1.0f,
            1.0f, 0.0f,
            0.0f,  0.0f,

    };


        private final String vertexShaderSource =
                    "attribute vec4 a_Position;" +
                    "void main() { " +
                    "     gl_Position = a_Position;" +
                            "gl_PointSize = 10.0;"  +
                    " }";

        private final String fragmentShaderSource =

                        "precision mediump float;" +
                        "uniform vec4 u_Color;" +
                        "void main()  {" +
                        "  gl_FragColor = u_Color;" +
                        "}";



    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
       // gl.glClearColor(0.7f, 0.1f, 0.2f, 0.2f);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);


        int vShaderObj;
        vShaderObj = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vShaderObj, vertexShaderSource);
        GLES20.glCompileShader(vShaderObj);

        int fShaderObj;
        fShaderObj = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fShaderObj, fragmentShaderSource);
        GLES20.glCompileShader(fShaderObj);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vShaderObj);
        GLES20.glAttachShader(mProgram, fShaderObj);
        GLES20.glLinkProgram(mProgram);

        // Lets create Native Memory for GPU.
        nativeBufferVertices = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        nativeBufferVertices.put(vertices);
        nativeBufferVertices.position(0);

        mPositionHandler = GLES20.glGetAttribLocation(mProgram, "a_Position");
        GLES20.glEnableVertexAttribArray(mPositionHandler);
        GLES20.glVertexAttribPointer(mPositionHandler, 2, GLES20.GL_FLOAT,false,0,nativeBufferVertices);


        GLES20.glUseProgram(mProgram);
        mUColorLocation = GLES20.glGetUniformLocation(mProgram, "u_Color");


        // drawingOrder Buffer
        /*

        nativedrawingListBuffer = ByteBuffer.allocateDirect(drawingOrder.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
        nativedrawingListBuffer.put(drawingOrder);
        nativedrawingListBuffer.position(0);

        */





    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

          glClear(GL_COLOR_BUFFER_BIT);

        // Draw the table.
        GLES20.glUniform4f(mUColorLocation, .2f, .1f, .5f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 0, 3);

        GLES20.glUniform4f(mUColorLocation, .4f, .2f, .1f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 3, 3);


    }
}

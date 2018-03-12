package com.frontier.lastkgb.airhockey.util;

import android.util.Log;

import static android.opengl.GLES20.*;

/**
 * Created by lastkgb on 2017-10-27.
 */

public class ShaderHelper {
    private static final String TAG = "ShaderHelper";

    public static int compileVertexShader(String shaderCode) {
        final int shaderObjectId = glCreateShader(GL_VERTEX_SHADER);
        if (shaderObjectId == 0) {
            Log.e(TAG, "Could not create new vertex shader");
            return 0;
        }

        glShaderSource(shaderObjectId, shaderCode);
        glCompileShader(shaderObjectId);

        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            glDeleteShader(shaderObjectId);
            Log.w(TAG, "Compilation of the vertex shader failed");
            return 0;
        }
        return shaderObjectId;
    }

    public static int compileFragmentShader(String shaderCode) {
        final int shaderObjectId = glCreateShader(GL_FRAGMENT_SHADER);
        if (shaderObjectId == 0) {
            Log.e(TAG, "Could not create new fragment shader");
            return 0;
        }

        glShaderSource(shaderObjectId, shaderCode);
        glCompileShader(shaderObjectId);

        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            glDeleteShader(shaderObjectId);
            Log.w(TAG, "Compilation of the fragment shader failed");
            return 0;
        }
        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        final int programObjectId = glCreateProgram();
        if (programObjectId == 0) {
            Log.w(TAG, "Could not create new program");
            return 0;
        }
        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);
        glLinkProgram(programObjectId);

        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            glDeleteProgram(programObjectId);
            Log.w(TAG, "Linking of program failed");
            return 0;
        }
        return programObjectId;
    }

    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);

        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
        Log.d(TAG, "Results of validating program: " + validateStatus[0] + "\nLog: " + glGetProgramInfoLog(programObjectId));

        return validateStatus[0] != 0;
    }
}

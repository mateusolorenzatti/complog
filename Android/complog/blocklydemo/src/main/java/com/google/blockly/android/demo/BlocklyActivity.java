/*
 *  Copyright 2016 Google Inc. All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.google.blockly.android.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.blockly.activity.AberturaActivity;
import com.google.blockly.activity.RespostaActivity;
import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;

import java.util.Arrays;
import java.util.List;

public class BlocklyActivity extends AbstractBlocklyActivity implements CodeGenerationRequest.CodeGeneratorCallback{
    private static final String TAG = "SimpleActivity";

    private static final String SAVE_FILENAME = "simple_workspace.xml";
    private static final String AUTOSAVE_FILENAME = "simple_workspace_temp.xml";

    // Add custom blocks to this list.
    private static final List<String> BLOCK_DEFINITIONS = Arrays.asList("complog/blocos.json");
    private static final List<String> JAVASCRIPT_GENERATORS = Arrays.asList("complog/gerador.js");

    private boolean conectada;
    private String back;

    private static final String TOOLBOX_PATH = "complog/toolbox.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.conectada = getIntent().getExtras().getBoolean("conexao");

        this.back = getIntent().getExtras().getString("back");

        if(getIntent().getExtras().getBoolean("limpar")){
            this.onClearWorkspace();
        }
    }

    @Override
    public void onBackPressed() {
        if(back.equals("desafio")){
            finish();
        }else{
            Intent intent = new Intent(BlocklyActivity.this, AberturaActivity.class);
            startActivity(intent);

            finishAffinity();
        }
    }

    @Override
    protected boolean conferirConexao() {
        if (this.conectada) {
            return true;
        } else {
            Toast.makeText(this.getApplicationContext(), "Robô não encontrado", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @NonNull
    @Override
    protected List<String> getBlockDefinitionsJsonPaths() {
        return BLOCK_DEFINITIONS;
    }

    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        return this.TOOLBOX_PATH;
    }

    @NonNull
    @Override
    protected List<String> getGeneratorsJsPaths() {
        return JAVASCRIPT_GENERATORS;
    }

    @NonNull
    @Override
    protected CodeGenerationRequest.CodeGeneratorCallback getCodeGenerationCallback() {
        // Uses the same callback for every generation call.
        return this;
    }

    @Override
    @NonNull
    protected String getWorkspaceSavePath() {
        return SAVE_FILENAME;
    }

    @Override
    @NonNull
    protected String getWorkspaceAutosavePath() {
        return AUTOSAVE_FILENAME;
    }

    @Override
    public void onFinishCodeGeneration(String generatedCode) {
        if (generatedCode.isEmpty()) {
            Toast.makeText(this.getApplicationContext(), "Algo deu errado na geração de Código.", Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(this.getApplicationContext(), generatedCode, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(BlocklyActivity.this, RespostaActivity.class);

            intent.putExtra("codigo", generatedCode);

            startActivity(intent);
        }
    }
}
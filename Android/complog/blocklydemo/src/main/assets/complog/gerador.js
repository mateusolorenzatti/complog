Blockly.JavaScript['avance'] = function(block) {
  var number_distancia = block.getFieldValue('distancia');

  var code = 'avance('+ number_distancia + ');\n';
  return code;
};

Blockly.JavaScript['vire'] = function(block) {
  var dropdown_angulo = block.getFieldValue('angulo');
  var dropdown_direcao = block.getFieldValue('direcao');

  var code = 'vire('+ dropdown_angulo +','+ dropdown_direcao +');\n';
  return code;
};

Blockly.JavaScript['condicional'] = function(block) {
  var statements_cond = Blockly.JavaScript.statementToCode(block, 'cond');

  var code = 'seEncontrar(){ \n' + statements_cond + '} \n';
  return code;
};

Blockly.JavaScript['la_o'] = function(block) {
  var statements_la_o = Blockly.JavaScript.statementToCode(block, 'laço');

  var code = 'repitaAteEncontrar(){ \n' + statements_la_o + '} \n';
  return code;
};

Blockly.JavaScript['print'] = function(block) {
  var text_palavra = block.getFieldValue('palavra');

  var code = 'diga(' + text_palavra + ');\n';
  return code;
};
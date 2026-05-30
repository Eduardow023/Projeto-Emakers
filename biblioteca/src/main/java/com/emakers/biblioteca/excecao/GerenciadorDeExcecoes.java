
package com.emakers.biblioteca.excecao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
 //Aqui é onde ficam as excecoes do que o usuario pode preencher nos campos!
@ControllerAdvice
public class GerenciadorDeExcecoes{

    // 1.Tratamento para Recursos Não Encontrados (404)
      //Captura quando dispararmos RuntimeException no buscarPorId()
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex){
        Map<String, Object> corpoResposta = new HashMap<>();
        corpoResposta.put("timestamp", LocalDateTime.now());
        corpoResposta.put("status", HttpStatus.NOT_FOUND.value());
        corpoResposta.put("erro", "Recurso não encontrado");
        corpoResposta.put("mensagem", ex.getMessage());

        return new ResponseEntity<>(corpoResposta, HttpStatus.NOT_FOUND);
    }

    // 2.Tratamento para Tipo de Parâmetro Incorreto na URL (400)
      //Se o usuário digitar /livros/abc em vez de um número ID
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex){
        Map<String, Object> corpoResposta = new HashMap<>();
        corpoResposta.put("timestamp", LocalDateTime.now());
        corpoResposta.put("status", HttpStatus.BAD_REQUEST.value());
        corpoResposta.put("erro", "Requisição Inválida");
        corpoResposta.put("mensagem", String.format("O parâmetro '%s' deveria ser do tipo %s", ex.getName(), ex.getRequiredType().getSimpleName()));

        return new ResponseEntity<>(corpoResposta, HttpStatus.BAD_REQUEST);
    }

    // 3.Tratamento para JSON Malformado ou Inválido no Corpo da Requisição (400)
      //Se o usuário esquecer uma vírgula ou mandar um texto quebrado
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleInvalidJson(HttpMessageNotReadableException ex){
        Map<String, Object> corpoResposta = new HashMap<>();
        corpoResposta.put("timestamp", LocalDateTime.now());
        corpoResposta.put("status", HttpStatus.BAD_REQUEST.value());
        corpoResposta.put("erro", "JSON Inválido");
        corpoResposta.put("mensagem", "O corpo da requisição contém erros de sintaxe ou dados incompatíveis.");

        return new ResponseEntity<>(corpoResposta, HttpStatus.BAD_REQUEST);
    }
}
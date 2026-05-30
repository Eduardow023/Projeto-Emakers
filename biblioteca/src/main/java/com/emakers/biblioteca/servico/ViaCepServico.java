package com.emakers.biblioteca.servico;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
// Utiliza o serviço Rest do springbot pra requisitar dados externos
@Service
public class ViaCepServico {
    
    public String buscarEnderecoPorCep(String cep){
        String cepLimpo = cep.replaceAll("[^0-9]", "");
        String url = "https://viacep.com.br/ws/" + cepLimpo + "/json/";
        
        RestTemplate restTemplate = new RestTemplate();
        
        try {
            Map<?, ?> resposta = restTemplate.getForObject(url, Map.class);
            
            if (resposta != null && resposta.containsKey("logradouro")) {
                return resposta.get("logradouro") + ", " + 
                       resposta.get("bairro") + " - " + 
                       resposta.get("localidade") + "/" + 
                       resposta.get("uf");
            }
        } catch (Exception e) {
            return "Endereço não disponível no momento";
        }
        
        return "CEP não encontrado";
    }
}

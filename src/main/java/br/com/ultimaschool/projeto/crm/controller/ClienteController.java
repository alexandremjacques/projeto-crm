package br.com.ultimaschool.projeto.crm.controller;

import br.com.ultimaschool.projeto.crm.repository.Cliente;
import br.com.ultimaschool.projeto.crm.service.ClienteService;
import br.com.ultimaschool.projeto.crm.service.erros.ClienteNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/crm/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> mostraDadosCliente(@PathVariable int id) {
        Cliente cliente;
        ResponseEntity<Cliente> responseEntity;

        try {
            cliente = clienteService.retornaCliente(id);
            responseEntity = new ResponseEntity<>(cliente, HttpStatus.OK);
        } catch (ClienteNaoEncontradoException e) {
            responseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeDadosCliente(@PathVariable int id) {
        ResponseEntity<Void> responseEntity;

        try {
            clienteService.removeCliente(id);
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ClienteNaoEncontradoException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    @PutMapping()
    public ResponseEntity<Void> alteraDadosCliente(@RequestBody Cliente novosDadosCliente) {
        ResponseEntity<Void> responseEntity;

        try {
            clienteService.alteraDadosCliente(novosDadosCliente);
            responseEntity = new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (ClienteNaoEncontradoException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    @PostMapping()
    public ResponseEntity<Void> criaCliente(@RequestBody Cliente novoCliente) {
        ResponseEntity<Void> responseEntity;

        clienteService.insereCliente(novoCliente);
        responseEntity = new ResponseEntity<>(HttpStatus.CREATED);

        return responseEntity;
    }
}

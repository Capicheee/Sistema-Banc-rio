# Sistema Bancário Frontend (React + Vite)

Este é o frontend do sistema bancário desenvolvido com React (Vite). Ele consome os endpoints REST do backend.

## Como executar

1. Instalar dependências:
```bash
cd /home/juliano/Codes/sistema-bancario-frontend
npm install
```

2. Rodar em modo dev (proxy para backend http://localhost:8080):
```bash
npm run dev
```

3. Abrir navegador: http://localhost:5173

## Funcionalidades

- Listar e criar Clientes
- Listar e criar Contas (vinculadas a clientes)
- Realizar Transferências entre contas (atualiza saldos em tempo real)

## Observações

- O backend deve estar rodando em http://localhost:8080 (use profile dev/H2 ou prod/MySQL).
- Configure o proxy no `vite.config.js` caso o backend rode em outra porta.
- Caso o backend use autenticação (Basic/JWT), adicione headers às chamadas em `src/api/api.js`.
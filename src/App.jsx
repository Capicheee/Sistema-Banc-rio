import { useState } from 'react'
import Clientes from './components/Clientes'
import Contas from './components/Contas'
import Transferencia from './components/Transferencia'
import Investimentos from './components/Investimentos'
import Emprestimos from './components/Emprestimos'
import Pix from './components/Pix'

export default function App() {
  const [aba, setAba] = useState('clientes')

  return (
    <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
      <h1>Sistema Bancário</h1>
      
      <div style={{ marginBottom: '20px' }}>
        <button onClick={() => setAba('clientes')} style={{ marginRight: '10px' }}>
          Clientes
        </button>
        <button onClick={() => setAba('contas')} style={{ marginRight: '10px' }}>
          Contas
        </button>
        <button onClick={() => setAba('transferencia')} style={{ marginRight: '10px' }}>
          Transferência
        </button>
        <button onClick={() => setAba('investimentos')} style={{ marginRight: '10px' }}>
          Investimentos
        </button>
        <button onClick={() => setAba('emprestimos')} style={{ marginRight: '10px' }}>
          Empréstimos
        </button>
        <button onClick={() => setAba('pix')}>
          PIX
        </button>
      </div>

      {aba === 'clientes' && <Clientes />}
      {aba === 'contas' && <Contas />}
      {aba === 'transferencia' && <Transferencia />}
      {aba === 'investimentos' && <Investimentos />}
      {aba === 'emprestimos' && <Emprestimos />}
      {aba === 'pix' && <Pix />}
    </div>
  )
}
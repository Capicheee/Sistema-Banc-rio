import React, { useState } from 'react'
import Clientes from './components/Clientes'
import Contas from './components/Contas'
import Transferencia from './components/Transferencia'
import Investimentos from './components/Investimentos'
import Emprestimos from './components/Emprestimos'
import Pix from './components/Pix'
import './styles/global.css'

function App() {
  const [abaAtiva, setAbaAtiva] = useState('clientes')

  const abas = [
    { id: 'clientes', label: 'Clientes' },
    { id: 'contas', label: 'Contas' },
    { id: 'transferencia', label: 'Transferências' },
    { id: 'investimentos', label: 'Investimentos' },
    { id: 'emprestimos', label: 'Empréstimos' },
    { id: 'pix', label: 'PIX' }
  ]

  const renderizarConteudo = () => {
    switch (abaAtiva) {
      case 'clientes':
        return <Clientes />
      case 'contas':
        return <Contas />
      case 'transferencia':
        return <Transferencia />
      case 'investimentos':
        return <Investimentos />
      case 'emprestimos':
        return <Emprestimos />
      case 'pix':
        return <Pix />
      default:
        return <Clientes />
    }
  }

  return (
    <div className="app-container">
      <header className="app-header fade-in">
        <h1>Sistema Bancário Digital</h1>
        <p>Gerencie suas finanças de forma simples e segura</p>
      </header>

      <nav className="tabs-container fade-in">
        <div className="tabs">
          {abas.map(aba => (
            <button
              key={aba.id}
              className={`tab-button ${abaAtiva === aba.id ? 'active' : ''}`}
              onClick={() => setAbaAtiva(aba.id)}
            >
              {aba.label}
            </button>
          ))}
        </div>
      </nav>

      <main className="content-area fade-in">
        {renderizarConteudo()}
      </main>
    </div>
  )
}

export default App
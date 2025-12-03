import React from 'react'
import Clientes from './components/Clientes'
import Contas from './components/Contas'
import Transferencia from './components/Transferencia'

export default function App() {
  return (
    <div className="app">
      <header>
        <h1>Sistema Bancário</h1>
      </header>
      <main>
        <div className="row">
          <section className="card">
            <Clientes />
          </section>
          <section className="card">
            <Contas />
          </section>
        </div>
        <div className="row">
          <section className="card" style={{ width: '100%' }}>
            <Transferencia />
          </section>
        </div>
      </main>
    </div>
  )
}
import React, { useEffect, useState } from 'react'
import { apiPost, apiDelete } from '../api/api'
import { useApp } from '../contexts/AppContext'

export default function Clientes() {
  const { clientes, loadClientes } = useApp()
  const [nome, setNome] = useState('')
  const [cpf, setCpf] = useState('')
  const [email, setEmail] = useState('')

  useEffect(() => { loadClientes() }, [loadClientes])

  async function handleCreate(e) {
    e.preventDefault()
    try {
      await apiPost('http://localhost:8080/api/clientes', { nome, cpf, email })
      alert('Cliente criado!')
      setNome('')
      setCpf('')
      setEmail('')
      loadClientes()
    } catch (err) {
      alert('Erro: ' + err.message)
    }
  }

  async function handleDelete(id, nome) {
    if (!confirm(`Tem certeza que deseja deletar o cliente "${nome}"?`)) return
    
    try {
      await apiDelete(`http://localhost:8080/api/clientes/${id}`)
      alert('Cliente deletado com sucesso!')
      loadClientes()
    } catch (err) {
      alert('Erro ao deletar: ' + err.message)
    }
  }

  return (
    <div>
      <h2>Clientes</h2>
      <form onSubmit={handleCreate} className="form">
        <input value={nome} onChange={e => setNome(e.target.value)} placeholder="Nome" required />
        <input value={cpf} onChange={e => setCpf(e.target.value)} placeholder="CPF (apenas números)" required />
        <input value={email} onChange={e => setEmail(e.target.value)} placeholder="Email" type="email" required />
        <button type="submit">Criar</button>
      </form>

      <h3>Lista de Clientes ({clientes.length})</h3>
      <ul>
        {clientes.map(c => (
          <li key={c.id}>
            <strong>{c.nome}</strong> - {c.cpf} - {c.email}
            <button 
              onClick={() => handleDelete(c.id, c.nome)}
              style={{ marginLeft: '10px', color: 'red', cursor: 'pointer' }}
            >
              Deletar
            </button>
          </li>
        ))}
      </ul>
    </div>
  )
}
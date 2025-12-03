import React, { useEffect, useState } from 'react'
import { apiGet, apiPost } from '../api/api'

export default function Clientes() {
  const [clientes, setClientes] = useState([])
  const [nome, setNome] = useState('')
  const [cpf, setCpf] = useState('')
  const [email, setEmail] = useState('')
  const [loading, setLoading] = useState(true)

  async function load() {
    setLoading(true)
    try {
      const data = await apiGet('/api/clientes')
      setClientes(data || [])
    } catch (e) {
      console.error(e)
      setClientes([])
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { load() }, [])

  async function handleCreate(e) {
    e.preventDefault()
    try {
      const novo = await apiPost('/api/clientes', { nome, cpf, email })
      setNome(''); setCpf(''); setEmail('')
      setClientes(prev => [...prev, novo])
    } catch (err) {
      alert(err.message)
    }
  }

  return (
    <div>
      <h2>Clientes</h2>
      <form onSubmit={handleCreate} className="form">
        <input value={nome} placeholder="Nome" onChange={e => setNome(e.target.value)} required />
        <input value={cpf} placeholder="CPF (apenas números)" onChange={e => setCpf(e.target.value)} required />
        <input value={email} placeholder="Email" onChange={e => setEmail(e.target.value)} required />
        <button type="submit">Criar</button>
      </form>

      {loading ? <div>Loading...</div> : (
        <ul className="list">
          {clientes.map(c => (
            <li key={c.id}>
              <strong>{c.nome}</strong> — {c.cpf} — {c.email}
            </li>
          ))}
        </ul>
      )}
    </div>
  )
}
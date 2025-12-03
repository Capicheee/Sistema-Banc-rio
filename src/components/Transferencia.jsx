import React, { useEffect, useState } from 'react'
import { apiGet, apiPost } from '../api/api'

export default function Transferencia() {
  const [contas, setContas] = useState([])
  const [origem, setOrigem] = useState('')
  const [destino, setDestino] = useState('')
  const [valor, setValor] = useState('')

  async function load() {
    try {
      const data = await apiGet('/api/contas')
      setContas(data || [])
    } catch (e) {
      console.error(e)
    }
  }
  useEffect(() => { load() }, [])

  async function handleTransfer(e) {
    e.preventDefault()
    if (!origem || !destino || !valor) return alert('Preencha todos os campos')
    try {
      const payload = { origemId: Number(origem), destinoId: Number(destino), valor: parseFloat(valor) }
      const t = await apiPost('/api/transacoes', payload)
      alert('Transferência criada: ' + (t.id ?? t))
      setValor('')
      load() // reload contas para atualizar saldos
    } catch (err) {
      alert(err.message)
    }
  }

  return (
    <div>
      <h2>Transferência</h2>
      <form onSubmit={handleTransfer} className="form">
        <select value={origem} onChange={e => setOrigem(e.target.value)} required>
          <option value="">Conta Origem</option>
          {contas.map(c => <option key={c.id} value={c.id}>{c.numero ?? c.numeroConta} — R$ {c.saldo}</option>)}
        </select>
        <select value={destino} onChange={e => setDestino(e.target.value)} required>
          <option value="">Conta Destino</option>
          {contas.map(c => <option key={c.id} value={c.id}>{c.numero ?? c.numeroConta} — R$ {c.saldo}</option>)}
        </select>
        <input value={valor} onChange={e => setValor(e.target.value)} placeholder="Valor" required />
        <button type="submit">Transferir</button>
      </form>
    </div>
  )
}
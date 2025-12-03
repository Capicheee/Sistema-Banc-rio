import React, { useEffect, useState } from 'react'
import { apiGet, apiPost } from '../api/api'

export default function Contas() {
  const [contas, setContas] = useState([])
  const [clientes, setClientes] = useState([])
  const [numero, setNumero] = useState('')
  const [saldo, setSaldo] = useState('')
  const [tipo, setTipo] = useState('CONTA_CORRENTE')
  const [clienteId, setClienteId] = useState('')
  const [loading, setLoading] = useState(true)

  async function load() {
    setLoading(true)
    try {
      const cdata = await apiGet('/api/contas')
      const clidata = await apiGet('/api/clientes')
      setContas(cdata || [])
      setClientes(clidata || [])
    } catch (e) {
      console.error(e)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { load() }, [])

  async function handleCreate(e) {
    e.preventDefault()
    if (!clienteId) return alert('Escolha um cliente')
    try {
      // Ajuste: envia numeroConta e clienteId simples
      const payload = {
        numeroConta: numero,
        saldo: parseFloat(saldo || 0),
        tipoConta: tipo,
        clienteId: Number(clienteId)
      }
      console.log('Enviando payload:', payload) // debug
      const nova = await apiPost('/api/contas', payload)
      setNumero(''); setSaldo(''); setTipo('CONTA_CORRENTE'); setClienteId('')
      load() // recarrega lista
    } catch (err) {
      console.error('Erro ao criar conta:', err)
      alert(err.message)
    }
  }

  return (
    <div>
      <h2>Contas</h2>

      <form onSubmit={handleCreate} className="form">
        <input value={numero} placeholder="Número" onChange={e => setNumero(e.target.value)} required />
        <input value={saldo} placeholder="Saldo inicial" type="number" step="0.01" onChange={e => setSaldo(e.target.value)} />
        <select value={tipo} onChange={e => setTipo(e.target.value)}>
          <option value="CONTA_CORRENTE">Conta Corrente</option>
          <option value="CONTA_POUPANCA">Conta Poupança</option>
        </select>
        <select value={clienteId} onChange={e => setClienteId(e.target.value)} required>
          <option value="">Selecione Cliente</option>
          {clientes.map(c => <option key={c.id} value={c.id}>{c.nome} ({c.cpf})</option>)}
        </select>
        <button type="submit">Criar Conta</button>
      </form>

      {loading ? <div>Loading...</div> : (
        <ul className="list">
          {contas.map(ct => (
            <li key={ct.id}>
              <strong>{ct.numeroConta ?? ct.numero}</strong> — R$ {ct.saldo} — {ct.tipoConta ?? ct.tipo_conta} — Cliente: {ct.cliente?.nome ?? '—'}
            </li>
          ))}
        </ul>
      )}
    </div>
  )
}
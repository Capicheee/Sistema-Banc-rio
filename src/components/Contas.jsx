import React, { useEffect, useState } from 'react'
import { apiPost, apiDelete } from '../api/api'
import { useApp } from '../contexts/AppContext'

export default function Contas() {
  const { contas, clientes, loadContas, loadClientes } = useApp()
  const [numeroConta, setNumeroConta] = useState('')
  const [saldo, setSaldo] = useState('')
  const [tipoConta, setTipoConta] = useState('CONTA_CORRENTE')
  const [clienteId, setClienteId] = useState('')

  useEffect(() => {
    loadContas()
    loadClientes()
  }, [loadContas, loadClientes])

  async function handleCreate(e) {
    e.preventDefault()
    try {
      await apiPost('http://localhost:8080/api/contas', {
        numeroConta,
        saldo: parseFloat(saldo),
        tipoConta,
        clienteId: parseInt(clienteId)
      })
      alert('Conta criada!')
      setNumeroConta('')
      setSaldo('')
      setTipoConta('CONTA_CORRENTE')
      setClienteId('')
      loadContas()
    } catch (err) {
      alert('Erro: ' + err.message)
    }
  }

  async function handleDelete(id, numeroConta) {
    if (!confirm(`Tem certeza que deseja deletar a conta "${numeroConta}"?`)) return
    
    try {
      await apiDelete(`http://localhost:8080/api/contas/${id}`)
      alert('Conta deletada com sucesso!')
      loadContas()
    } catch (err) {
      alert('Erro ao deletar: ' + err.message)
    }
  }

  return (
    <div>
      <h2>Contas</h2>
      <form onSubmit={handleCreate} className="form">
        <input 
          value={numeroConta} 
          onChange={e => setNumeroConta(e.target.value)} 
          placeholder="Número" 
          required 
        />
        <input 
          type="number" 
          step="0.01" 
          value={saldo} 
          onChange={e => setSaldo(e.target.value)} 
          placeholder="Saldo inicial" 
          required 
        />
        <select value={tipoConta} onChange={e => setTipoConta(e.target.value)}>
          <option value="CONTA_CORRENTE">Conta Corrente</option>
          <option value="CONTA_POUPANCA">Conta Poupança</option>
        </select>
        <select value={clienteId} onChange={e => setClienteId(e.target.value)} required>
          <option value="">Selecione Cliente</option>
          {clientes.map(c => (
            <option key={c.id} value={c.id}>{c.nome}</option>
          ))}
        </select>
        <button type="submit">Criar Conta</button>
      </form>

      <h3>Total: {contas.length} conta(s)</h3>
      <ul>
        {contas.map(c => (
          <li key={c.id}>
            <strong>{c.numeroConta}</strong> - R$ {c.saldo?.toFixed(2)} - {c.tipoConta?.replace('_', ' ')} - Cliente: {c.clienteNome}
            <button 
              onClick={() => handleDelete(c.id, c.numeroConta)}
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
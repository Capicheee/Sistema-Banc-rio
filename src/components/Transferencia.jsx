import React, { useEffect, useState } from 'react'
import { apiPost } from '../api/api'
import { useApp } from '../contexts/AppContext'

export default function Transferencia() {
  const { contas, loadContas } = useApp()
  const [origem, setOrigem] = useState('')
  const [destino, setDestino] = useState('')
  const [valor, setValor] = useState('')

  useEffect(() => { loadContas() }, [loadContas])

  async function handleTransfer(e) {
    e.preventDefault()
    if (!origem || !destino || !valor) return alert('Preencha todos os campos')
    try {
      const payload = { 
        contaOrigemId: Number(origem), 
        contaDestinoId: Number(destino), 
        valor: parseFloat(valor) 
      }
      const resultado = await apiPost('http://localhost:8080/api/transacoes/transferir', payload)
      alert(resultado.mensagem || 'Transferência realizada com sucesso!')
      setOrigem('')
      setDestino('')
      setValor('')
      loadContas()
    } catch (err) {
      alert('Erro: ' + err.message)
    }
  }

  return (
    <div>
      <h2>Transferência</h2>
      <form onSubmit={handleTransfer} className="form">
        <select value={origem} onChange={e => setOrigem(e.target.value)} required>
          <option value="">Conta Origem</option>
          {contas.map(c => (
            <option key={c.id} value={c.id}>
              {c.numeroConta} - {c.clienteNome} - R$ {c.saldo?.toFixed(2)}
            </option>
          ))}
        </select>
        <select value={destino} onChange={e => setDestino(e.target.value)} required>
          <option value="">Conta Destino</option>
          {contas.map(c => (
            <option key={c.id} value={c.id}>
              {c.numeroConta} - {c.clienteNome}
            </option>
          ))}
        </select>
        <input 
          type="number" 
          step="0.01" 
          value={valor} 
          onChange={e => setValor(e.target.value)} 
          placeholder="Valor" 
          required 
        />
        <button type="submit">Transferir</button>
      </form>
    </div>
  )
}
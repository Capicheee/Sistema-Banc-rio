import React, { useEffect, useState } from 'react'
import { apiPost, apiDelete } from '../api/api'
import { useApp } from '../contexts/AppContext'

export default function Investimentos() {
  const { contas, investimentos, loadContas, loadInvestimentos } = useApp()
  const [contaId, setContaId] = useState('')
  const [tipo, setTipo] = useState('CDB')
  const [valor, setValor] = useState('')
  const [rentabilidade, setRentabilidade] = useState('')

  useEffect(() => {
    loadContas()
    loadInvestimentos()
  }, [loadContas, loadInvestimentos])

  async function handleCreate(e) {
    e.preventDefault()
    try {
      await apiPost('http://localhost:8080/api/investimentos', {
        contaId: parseInt(contaId),
        tipo,
        valor: parseFloat(valor),
        rentabilidade: parseFloat(rentabilidade)
      })
      alert('Investimento criado com sucesso!')
      setContaId('')
      setTipo('CDB')
      setValor('')
      setRentabilidade('')
      loadInvestimentos()
      loadContas() // Atualiza saldos
    } catch (err) {
      alert('Erro: ' + err.message)
    }
  }

  async function handleResgatar(id) {
    if (!confirm('Tem certeza que deseja resgatar este investimento?')) return
    
    try {
      const resultado = await apiPost(`http://localhost:8080/api/investimentos/${id}/resgatar`, {})
      alert(`${resultado.mensagem}\nValor investido: R$ ${resultado.valorInvestido.toFixed(2)}\nValor resgatado: R$ ${resultado.valorResgatado.toFixed(2)}\nRendimento: R$ ${resultado.rendimento.toFixed(2)}`)
      loadInvestimentos()
      loadContas() // Atualiza saldos
    } catch (err) {
      alert('Erro ao resgatar: ' + err.message)
    }
  }

  async function handleDelete(id) {
    if (!confirm('Tem certeza que deseja deletar este investimento?')) return
    
    try {
      await apiDelete(`http://localhost:8080/api/investimentos/${id}`)
      alert('Investimento deletado com sucesso!')
      loadInvestimentos()
    } catch (err) {
      alert('Erro ao deletar: ' + err.message)
    }
  }

  const investimentosAtivos = investimentos.filter(i => i.status === 'ATIVO')
  const investimentosResgatados = investimentos.filter(i => i.status === 'RESGATADO')

  return (
    <div>
      <h2>Investimentos</h2>
      <form onSubmit={handleCreate} className="form">
        <select value={contaId} onChange={e => setContaId(e.target.value)} required>
          <option value="">Selecione a Conta</option>
          {contas.map(c => (
            <option key={c.id} value={c.id}>
              {c.numeroConta} - {c.clienteNome} - R$ {c.saldo?.toFixed(2)}
            </option>
          ))}
        </select>
        
        <select value={tipo} onChange={e => setTipo(e.target.value)}>
          <option value="CDB">CDB</option>
          <option value="LCI">LCI</option>
          <option value="LCA">LCA</option>
          <option value="TESOURO_DIRETO">Tesouro Direto</option>
          <option value="FUNDOS">Fundos</option>
        </select>

        <input 
          type="number" 
          step="0.01" 
          value={valor} 
          onChange={e => setValor(e.target.value)} 
          placeholder="Valor" 
          required 
        />

        <input 
          type="number" 
          step="0.01" 
          value={rentabilidade} 
          onChange={e => setRentabilidade(e.target.value)} 
          placeholder="Rentabilidade (% ao ano)" 
          required 
        />

        <button type="submit">Investir</button>
      </form>

      <h3>Investimentos Ativos ({investimentosAtivos.length})</h3>
      <ul>
        {investimentosAtivos.map(inv => (
          <li key={inv.id}>
            <strong>{inv.tipo}</strong> - R$ {inv.valor?.toFixed(2)} - {inv.rentabilidade}% a.a. - 
            Conta: {inv.numeroConta} ({inv.clienteNome}) - 
            Início: {inv.dataInicio}
            <button 
              onClick={() => handleResgatar(inv.id)}
              style={{ marginLeft: '10px', color: 'green', cursor: 'pointer' }}
            >
              Resgatar
            </button>
          </li>
        ))}
      </ul>

      <h3>Investimentos Resgatados ({investimentosResgatados.length})</h3>
      <ul>
        {investimentosResgatados.map(inv => (
          <li key={inv.id}>
            <strong>{inv.tipo}</strong> - R$ {inv.valor?.toFixed(2)} - {inv.rentabilidade}% a.a. - 
            Conta: {inv.numeroConta} ({inv.clienteNome}) - 
            Resgatado em: {inv.dataResgate}
            <button 
              onClick={() => handleDelete(inv.id)}
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
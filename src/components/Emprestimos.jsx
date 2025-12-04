import React, { useEffect, useState } from 'react'
import { apiPost, apiDelete } from '../api/api'
import { useApp } from '../contexts/AppContext'

export default function Emprestimos() {
  const { contas, emprestimos, loadContas, loadEmprestimos } = useApp()
  const [contaId, setContaId] = useState('')
  const [valorSolicitado, setValorSolicitado] = useState('')
  const [numeroParcelas, setNumeroParcelas] = useState('12')
  const [taxaJuros, setTaxaJuros] = useState('2.5')

  useEffect(() => {
    loadContas()
    loadEmprestimos()
  }, [loadContas, loadEmprestimos])

  async function handleContratar(e) {
    e.preventDefault()
    try {
      const resultado = await apiPost('http://localhost:8080/api/emprestimos', {
        contaId: parseInt(contaId),
        valorSolicitado: parseFloat(valorSolicitado),
        numeroParcelas: parseInt(numeroParcelas),
        taxaJuros: parseFloat(taxaJuros)
      })
      alert(`${resultado.mensagem}\nValor solicitado: R$ ${resultado.valorSolicitado.toFixed(2)}\nValor total: R$ ${resultado.valorTotal.toFixed(2)}\nParcelas: ${numeroParcelas}x R$ ${resultado.valorParcela.toFixed(2)}`)
      setContaId('')
      setValorSolicitado('')
      setNumeroParcelas('12')
      setTaxaJuros('2.5')
      loadEmprestimos()
      loadContas()
    } catch (err) {
      alert('Erro: ' + err.message)
    }
  }

  async function handlePagarParcela(id) {
    if (!confirm('Tem certeza que deseja pagar esta parcela?')) return
    
    try {
      const resultado = await apiPost(`http://localhost:8080/api/emprestimos/${id}/pagar-parcela`, {})
      alert(`${resultado.mensagem}\nParcelas pagas: ${resultado.parcelasPagas}\nParcelas restantes: ${resultado.parcelasRestantes}\nNovo saldo: R$ ${resultado.novoSaldo.toFixed(2)}${resultado.quitado ? '\n\nEmpréstimo QUITADO!' : ''}`)
      loadEmprestimos()
      loadContas()
    } catch (err) {
      alert('Erro ao pagar parcela: ' + err.message)
    }
  }

  async function handleDelete(id) {
    if (!confirm('Tem certeza que deseja deletar este empréstimo?')) return
    
    try {
      await apiDelete(`http://localhost:8080/api/emprestimos/${id}`)
      alert('Empréstimo deletado com sucesso!')
      loadEmprestimos()
    } catch (err) {
      alert('Erro ao deletar: ' + err.message)
    }
  }

  const emprestimosAtivos = emprestimos.filter(e => e.status === 'ATIVO')
  const emprestimosQuitados = emprestimos.filter(e => e.status === 'QUITADO')

  return (
    <div>
      <h2>Empréstimos</h2>
      <form onSubmit={handleContratar} className="form">
        <select value={contaId} onChange={e => setContaId(e.target.value)} required>
          <option value="">Selecione a Conta</option>
          {contas.map(c => (
            <option key={c.id} value={c.id}>
              {c.numeroConta} - {c.clienteNome} - R$ {c.saldo?.toFixed(2)}
            </option>
          ))}
        </select>

        <input 
          type="number" 
          step="0.01" 
          value={valorSolicitado} 
          onChange={e => setValorSolicitado(e.target.value)} 
          placeholder="Valor solicitado" 
          required 
        />

        <select value={numeroParcelas} onChange={e => setNumeroParcelas(e.target.value)}>
          <option value="6">6 parcelas</option>
          <option value="12">12 parcelas</option>
          <option value="18">18 parcelas</option>
          <option value="24">24 parcelas</option>
          <option value="36">36 parcelas</option>
          <option value="48">48 parcelas</option>
        </select>

        <input 
          type="number" 
          step="0.01" 
          value={taxaJuros} 
          onChange={e => setTaxaJuros(e.target.value)} 
          placeholder="Taxa de juros (% ao mês)" 
          required 
        />

        <button type="submit">Contratar Empréstimo</button>
      </form>

      <h3>Empréstimos Ativos ({emprestimosAtivos.length})</h3>
      <ul>
        {emprestimosAtivos.map(emp => (
          <li key={emp.id}>
            <strong>R$ {emp.valorTotal?.toFixed(2)}</strong> - 
            {emp.numeroParcelas}x R$ {emp.valorParcela?.toFixed(2)} - 
            Taxa: {emp.taxaJuros}% a.m. - 
            Conta: {emp.numeroConta} ({emp.clienteNome}) - 
            Pagas: {emp.parcelasPagas}/{emp.numeroParcelas} - 
            Contratado em: {emp.dataContratacao}
            <button 
              onClick={() => handlePagarParcela(emp.id)}
              style={{ marginLeft: '10px', color: 'blue', cursor: 'pointer' }}
            >
              Pagar Parcela
            </button>
          </li>
        ))}
      </ul>

      <h3>Empréstimos Quitados ({emprestimosQuitados.length})</h3>
      <ul>
        {emprestimosQuitados.map(emp => (
          <li key={emp.id}>
            <strong>R$ {emp.valorTotal?.toFixed(2)}</strong> - 
            {emp.numeroParcelas}x R$ {emp.valorParcela?.toFixed(2)} - 
            Taxa: {emp.taxaJuros}% a.m. - 
            Conta: {emp.numeroConta} ({emp.clienteNome}) - 
            Quitado em: {emp.dataQuitacao}
            <button 
              onClick={() => handleDelete(emp.id)}
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
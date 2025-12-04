import React, { useEffect, useState } from 'react'
import { apiPost, apiDelete } from '../api/api'
import { useApp } from '../contexts/AppContext'

export default function Pix() {
  const { contas, pixList, loadContas, loadPix } = useApp()
  const [aba, setAba] = useState('gerar')
  
  // Gerar PIX
  const [contaDestinoId, setContaDestinoId] = useState('')
  const [chaveDestino, setChaveDestino] = useState('')
  const [valorGerar, setValorGerar] = useState('')
  const [pixGerado, setPixGerado] = useState(null)

  // Pagar PIX
  const [contaOrigemId, setContaOrigemId] = useState('')
  const [codigoPix, setCodigoPix] = useState('')

  useEffect(() => {
    loadContas()
    loadPix()
  }, [loadContas, loadPix])

  async function handleGerarPix(e) {
    e.preventDefault()
    try {
      const resultado = await apiPost('http://localhost:8080/api/pix/gerar', {
        contaDestinoId: parseInt(contaDestinoId),
        chaveDestino,
        valor: parseFloat(valorGerar)
      })
      
      setPixGerado(resultado.pix)
      alert(resultado.mensagem)
      setContaDestinoId('')
      setChaveDestino('')
      setValorGerar('')
      loadPix()
    } catch (err) {
      alert('Erro: ' + err.message)
    }
  }

  async function handlePagarPorCodigo(e) {
    e.preventDefault()
    try {
      const resultado = await apiPost('http://localhost:8080/api/pix/pagar-por-codigo', {
        contaOrigemId: parseInt(contaOrigemId),
        codigoPix
      })
      
      alert(`${resultado.mensagem}\nValor pago: R$ ${resultado.valorPago?.toFixed(2)}\nDestinatário: ${resultado.destinatario}\nNovo saldo: R$ ${resultado.novoSaldo?.toFixed(2)}`)
      setContaOrigemId('')
      setCodigoPix('')
      loadPix()
      loadContas()
    } catch (err) {
      alert('Erro: ' + err.message)
    }
  }

  async function handlePagarPixDaLista(pixId) {
    const contaId = prompt('Digite o ID da sua conta para pagar:')
    if (!contaId) return

    try {
      const resultado = await apiPost(`http://localhost:8080/api/pix/${pixId}/pagar`, {
        contaOrigemId: parseInt(contaId)
      })
      
      alert(`${resultado.mensagem}\nValor pago: R$ ${resultado.valorPago?.toFixed(2)}\nNovo saldo: R$ ${resultado.novoSaldoOrigem?.toFixed(2)}`)
      loadPix()
      loadContas()
    } catch (err) {
      alert('Erro: ' + err.message)
    }
  }

  async function handleCancelar(id) {
    if (!confirm('Tem certeza que deseja cancelar este PIX?')) return
    
    try {
      await apiDelete(`http://localhost:8080/api/pix/${id}`)
      alert('PIX cancelado com sucesso!')
      loadPix()
      setPixGerado(null)
    } catch (err) {
      alert('Erro: ' + err.message)
    }
  }

  const pixPendentes = pixList.filter(p => p.status === 'PENDENTE')
  const pixPagos = pixList.filter(p => p.status === 'PAGO')

  return (
    <div>
      <h2>PIX</h2>
      
      <div style={{ marginBottom: '20px' }}>
        <button onClick={() => setAba('gerar')} style={{ marginRight: '10px' }}>
          Gerar PIX
        </button>
        <button onClick={() => setAba('pagar')}>
          Pagar PIX
        </button>
      </div>

      {aba === 'gerar' && (
        <div>
          <h3>Gerar QR Code PIX</h3>
          <form onSubmit={handleGerarPix} className="form">
            <select value={contaDestinoId} onChange={e => setContaDestinoId(e.target.value)} required>
              <option value="">Selecione sua conta (destino)</option>
              {contas.map(c => (
                <option key={c.id} value={c.id}>
                  {c.numeroConta} - {c.clienteNome} - R$ {c.saldo?.toFixed(2)}
                </option>
              ))}
            </select>

            <input 
              value={chaveDestino} 
              onChange={e => setChaveDestino(e.target.value)} 
              placeholder="Chave PIX (CPF, email, telefone)" 
              required 
            />

            <input 
              type="number" 
              step="0.01" 
              value={valorGerar} 
              onChange={e => setValorGerar(e.target.value)} 
              placeholder="Valor" 
              required 
            />

            <button type="submit">Gerar QR Code</button>
          </form>

          {pixGerado && (
            <div style={{ marginTop: '20px', padding: '20px', border: '2px solid #4CAF50', borderRadius: '8px', backgroundColor: '#f9f9f9' }}>
              <h3>PIX Gerado com Sucesso!</h3>
              <p><strong>Código PIX:</strong> {pixGerado.codigoPix}</p>
              <p><strong>Valor:</strong> R$ {pixGerado.valor?.toFixed(2)}</p>
              <p><strong>Chave:</strong> {pixGerado.chaveDestino}</p>
              <p><strong>Destinatário:</strong> {pixGerado.clienteDestinoNome}</p>
              
              <div style={{ textAlign: 'center', marginTop: '20px' }}>
                <img 
                  src={pixGerado.qrCodeBase64} 
                  alt="QR Code PIX" 
                  style={{ maxWidth: '300px', border: '1px solid #ccc', padding: '10px', backgroundColor: 'white' }}
                />
              </div>

              <p style={{ marginTop: '20px', fontSize: '12px', color: '#666' }}>
                Escaneie o QR Code ou use o código <strong>{pixGerado.codigoPix}</strong> para pagar
              </p>

              <button 
                onClick={() => handleCancelar(pixGerado.id)}
                style={{ marginTop: '10px', color: 'red' }}
              >
                Cancelar PIX
              </button>
            </div>
          )}
        </div>
      )}

      {aba === 'pagar' && (
        <div>
          <h3>Pagar PIX por Código</h3>
          <form onSubmit={handlePagarPorCodigo} className="form">
            <select value={contaOrigemId} onChange={e => setContaOrigemId(e.target.value)} required>
              <option value="">Selecione sua conta (origem)</option>
              {contas.map(c => (
                <option key={c.id} value={c.id}>
                  {c.numeroConta} - {c.clienteNome} - R$ {c.saldo?.toFixed(2)}
                </option>
              ))}
            </select>

            <input 
              value={codigoPix} 
              onChange={e => setCodigoPix(e.target.value)} 
              placeholder="Cole o código PIX aqui" 
              required 
            />

            <button type="submit">Pagar PIX</button>
          </form>
        </div>
      )}

      <h3>PIX Pendentes ({pixPendentes.length})</h3>
      <ul>
        {pixPendentes.map(pix => (
          <li key={pix.id}>
            <strong>R$ {pix.valor?.toFixed(2)}</strong> - 
            Código: {pix.codigoPix} - 
            Chave: {pix.chaveDestino} - 
            Destino: {pix.clienteDestinoNome} ({pix.numeroContaDestino}) - 
            Gerado em: {pix.dataGeracao}
            <button 
              onClick={() => handlePagarPixDaLista(pix.id)}
              style={{ marginLeft: '10px', color: 'blue', cursor: 'pointer' }}
            >
              Pagar
            </button>
            <button 
              onClick={() => handleCancelar(pix.id)}
              style={{ marginLeft: '10px', color: 'red', cursor: 'pointer' }}
            >
              Cancelar
            </button>
          </li>
        ))}
      </ul>

      <h3>PIX Pagos ({pixPagos.length})</h3>
      <ul>
        {pixPagos.map(pix => (
          <li key={pix.id}>
            <strong>R$ {pix.valor?.toFixed(2)}</strong> - 
            De: {pix.clienteOrigemNome} ({pix.numeroContaOrigem}) - 
            Para: {pix.clienteDestinoNome} ({pix.numeroContaDestino}) - 
            Pago em: {pix.dataPagamento}
          </li>
        ))}
      </ul>
    </div>
  )
}
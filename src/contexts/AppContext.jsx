import React, { createContext, useContext, useState, useCallback } from 'react'
import { apiGet } from '../api/api'

const AppContext = createContext()

export function AppProvider({ children }) {
  const [clientes, setClientes] = useState([])
  const [contas, setContas] = useState([])
  const [investimentos, setInvestimentos] = useState([])
  const [emprestimos, setEmprestimos] = useState([])
  const [pixList, setPixList] = useState([])

  const loadClientes = useCallback(async () => {
    try {
      const data = await apiGet('http://localhost:8080/api/clientes')
      setClientes(data || [])
    } catch (e) {
      console.error(e)
    }
  }, [])

  const loadContas = useCallback(async () => {
    try {
      const data = await apiGet('http://localhost:8080/api/contas')
      setContas(data || [])
    } catch (e) {
      console.error(e)
    }
  }, [])

  const loadInvestimentos = useCallback(async () => {
    try {
      const data = await apiGet('http://localhost:8080/api/investimentos')
      setInvestimentos(data || [])
    } catch (e) {
      console.error(e)
    }
  }, [])

  const loadEmprestimos = useCallback(async () => {
    try {
      const data = await apiGet('http://localhost:8080/api/emprestimos')
      setEmprestimos(data || [])
    } catch (e) {
      console.error(e)
    }
  }, [])

  const loadPix = useCallback(async () => {
    try {
      const data = await apiGet('http://localhost:8080/api/pix')
      setPixList(data || [])
    } catch (e) {
      console.error(e)
    }
  }, [])

  return (
    <AppContext.Provider value={{ 
      clientes, contas, investimentos, emprestimos, pixList,
      loadClientes, loadContas, loadInvestimentos, loadEmprestimos, loadPix
    }}>
      {children}
    </AppContext.Provider>
  )
}

export function useApp() {
  return useContext(AppContext)
}
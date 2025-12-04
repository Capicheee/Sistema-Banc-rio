export async function apiGet(path) {
  const res = await fetch(path, { 
    headers: { 
      'Content-Type': 'application/json'
    } 
  })
  if (!res.ok) throw new Error(`GET ${path} failed: ${res.status}`)
  return res.json()
}

export async function apiPost(path, payload) {
  const res = await fetch(path, {
    method: 'POST',
    headers: { 
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(payload),
  })
  if (!res.ok) {
    const text = await res.text().catch(() => '')
    throw new Error(`POST ${path} failed: ${res.status} ${text}`)
  }
  return res.json()
}

export async function apiDelete(path) {
  const res = await fetch(path, {
    method: 'DELETE',
    headers: { 
      'Content-Type': 'application/json'
    }
  })
  if (!res.ok) {
    const text = await res.text().catch(() => '')
    throw new Error(`DELETE ${path} failed: ${res.status} ${text}`)
  }
  return res.json()
}
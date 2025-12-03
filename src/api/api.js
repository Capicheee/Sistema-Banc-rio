const AUTH = 'Basic ' + btoa('admin:admin'); // usuário:senha do Spring Security

export async function apiGet(path) {
  const res = await fetch(path, { 
    headers: { 
      'Content-Type': 'application/json',
      'Authorization': AUTH
    } 
  })
  if (!res.ok) throw new Error(`GET ${path} failed: ${res.status}`)
  return res.json()
}

export async function apiPost(path, payload) {
  const res = await fetch(path, {
    method: 'POST',
    headers: { 
      'Content-Type': 'application/json',
      'Authorization': AUTH
    },
    body: JSON.stringify(payload),
  })
  if (!res.ok) {
    const text = await res.text().catch(() => '')
    throw new Error(`POST ${path} failed: ${res.status} ${text}`)
  }
  return res.json()
}
const API_BASE = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

async function request(path, options = {}) {
  const res = await fetch(`${API_BASE}${path}`, {
    headers: { "Content-Type": "application/json", ...(options.headers || {}) },
    ...options,
  });

  if (!res.ok) {
    let msg = `HTTP ${res.status}`;
    try {
      const err = await res.json();
      msg = err?.message || msg;
    } catch {}
    throw new Error(msg);
  }

  if (res.status === 204) return null;


  const text = await res.text();
  if (!text) return null;


  return JSON.parse(text);
}

export const NotesApi = {
  list: () => request("/api/notes"),
  create: (payload) =>
    request("/api/notes", { method: "POST", body: JSON.stringify(payload) }),
  update: (id, payload) =>
    request(`/api/notes/${id}`, { method: "PUT", body: JSON.stringify(payload) }),
  remove: (id) => request(`/api/notes/${id}`, { method: "DELETE" }),
};


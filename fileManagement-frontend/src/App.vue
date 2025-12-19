<template>
  <div class="bg">
    <div class="wrap">
      <header class="hero">
        <div class="heroLeft">
          <h1>fileManagement</h1>
        </div>
      </header>

      <main class="grid">
        <!-- Form -->
        <section class="card formCard">
          <div class="cardHead">
            <div>
              <h2>{{ editing ? "Edit Note" : "Create Note" }}</h2>
              <span class="sub">
                {{ editing ? "Update your note content" : "Add a new note to your list" }}
              </span>
            </div>

            <button v-if="editing" class="btn ghost" @click="cancelEdit">Cancel</button>
          </div>

          <div class="field">
            <label class="label">Title</label>
            <input class="input" v-model.trim="form.title" placeholder="e.g., Meeting notes" />
          </div>

          <div class="field">
            <label class="label">Content</label>
            <textarea
              class="textarea"
              v-model.trim="form.content"
              rows="8"
              placeholder="Write your note..."
            ></textarea>
          </div>

          <div class="row">
            <button class="btn primary" :disabled="saving" @click="save">
              {{ saving ? "Saving..." : editing ? "Update" : "Add Note" }}
            </button>
            <button class="btn ghost" :disabled="loading" @click="load">Refresh</button>
          </div>

          <p v-if="error" class="error">{{ error }}</p>
          <p class="hint">Tip: Click a note card to edit. Delete needs confirmation.</p>
        </section>

        <!-- List -->
        <section class="card listCard">
          <div class="topbar">
            <div>
              <h2>All Notes</h2>
              <span class="sub">{{ notes.length }} item(s)</span>
            </div>

            <input class="search" v-model.trim="q" placeholder="Search title/content..." />
          </div>

          <div class="divider"></div>

          <div v-if="loading" class="empty">
            <div class="spinner"></div>
            Loading notes...
          </div>

          <div v-else-if="filtered.length === 0" class="empty">
            <div class="emptyIcon">📝</div>
            <div class="emptyTitle">No notes yet</div>
            <div class="emptyText">Create your first note on the left.</div>
          </div>

          <div v-else class="list">
            <article
              v-for="n in filtered"
              :key="n.id"
              class="note"
              @click="startEdit(n)"
              :class="{ active: editing && editingId === n.id }"
            >
              <div class="noteHead">
                <h3 class="title">{{ n.title }}</h3>
                <span class="time">{{ fmt(n.updatedAt) }}</span>
              </div>

              <p class="content">{{ n.content }}</p>

              <div class="actions" @click.stop>
                <button class="btn xs ghost" @click="startEdit(n)">Edit</button>
                <button class="btn xs danger" @click="remove(n)">Delete</button>
              </div>
            </article>
          </div>
        </section>
      </main>

    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { NotesApi } from "./api.js";

const apiBase = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

const notes = ref([]);
const loading = ref(false);
const saving = ref(false);
const error = ref("");

const q = ref("");

const editing = ref(false);
const editingId = ref(null);
const form = ref({ title: "", content: "" });

const filtered = computed(() => {
  const s = q.value.toLowerCase();
  if (!s) return notes.value;
  return notes.value.filter(
    (n) =>
      (n.title || "").toLowerCase().includes(s) ||
      (n.content || "").toLowerCase().includes(s)
  );
});

function fmt(iso) {
  if (!iso) return "";
  return new Date(iso).toLocaleString();
}

async function load() {
  error.value = "";
  loading.value = true;
  try {
    notes.value = await NotesApi.list();
  } catch (e) {
    error.value = e?.message || "Failed to load notes.";
  } finally {
    loading.value = false;
  }
}

function startEdit(n) {
  editing.value = true;
  editingId.value = n.id;
  form.value = { title: n.title, content: n.content };
}

function cancelEdit() {
  editing.value = false;
  editingId.value = null;
  form.value = { title: "", content: "" };
}

async function save() {
  error.value = "";
  const title = (form.value.title || "").trim();
  const content = (form.value.content || "").trim();

  if (!title || !content) {
    error.value = "Please fill in both title and content.";
    return;
  }

  saving.value = true;
  try {
    if (!editing.value) {
      await NotesApi.create({ title, content });
      form.value = { title: "", content: "" };
    } else {
      await NotesApi.update(editingId.value, { title, content });
      cancelEdit();
    }
    await load();
  } catch (e) {
    error.value = e?.message || "Save failed.";
  } finally {
    saving.value = false;
  }
}

async function remove(n) {
  const ok = confirm(`Delete "${n.title}"?`);
  if (!ok) return;

  error.value = "";
  loading.value = true; 
  try {
    await NotesApi.remove(n.id);

    if (editing.value && editingId.value === n.id) cancelEdit();

    await load();
  } catch (e) {
    error.value = e?.message || "Delete failed.";
  } finally {
    loading.value = false;
  }
}

onMounted(load);
</script>


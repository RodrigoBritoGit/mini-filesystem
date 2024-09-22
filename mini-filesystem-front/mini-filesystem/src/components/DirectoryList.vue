<template>
 <div style="margin-bottom: 30px;">
  <nav class="navbar">
        <a href="#" class="navbar-brand">FileSystem</a>
        <ul class="navbar-nav">
            <li class="nav-item"><a href="#" class="nav-link">Início</a></li>
            <li class="nav-item"><a href="#" class="nav-link">Configurações</a></li>
            <li class="nav-item"><a href="#" class="nav-link">Sair</a></li>
        </ul>
    </nav>
 </div>

 <div class="container">
    <div class="sidebar" style="min-width: 200px;">
        <h2>Diretórios</h2>
        <ul style="min-width: 200px;">
            <li class="lista" 
                v-for="directory in directories"
                :key="directory.id"
                @click="selectDirectory(directory)"
                :class="{ selected: selectedDirectory && selectedDirectory.id === directory.id }"
            >
                {{ directory.name }}
                <div class="button-group">
                    <button class="btn-editar" v-b-modal.modal-prevent-closing @click.stop="editDirectory(directory)">Editar</button>
                    <button class="btn-excluir" @click.stop="confirmDelete(directory.id)">Excluir</button>
                </div>
            </li>
        </ul>
        <button class="btn-editar" @click="showCreateModal = true">Criar Diretório</button>
    </div>

    <div class="main-content">
      <h2>Arquivos no Diretório: {{ selectedDirectory ? selectedDirectory.name : 'Selecione um diretório' }}</h2>
      <button class="btn-editar" @click="showUploadModal = true" v-if="selectedDirectory">Processar Arquivo</button>

      <table class="tabela" v-if="selectedDirectory && files.length > 0">
        <thead>
          <tr>
            <th>Nome do Arquivo</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="file in files" :key="file.id">
            <td>{{ file.name }}</td>
            <td>
              <button class="btn-editar" @click="editFile(file)">Editar</button>
              <button class="btn-excluir" @click="confirmDeleteFile(file.id)">Excluir</button>
              <button class="btn-download" @click="downloadFile(file.id)">Download</button>
            </td>
          </tr>
        </tbody>
      </table>
      <p v-else-if="selectedDirectory">Nenhum arquivo encontrado.</p>
    </div>

    <!-- Modal para Criar Diretório -->
    <div class="modal" v-if="showCreateModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Criar Diretório</h5>
          </div>
          <div class="modal-body">
            <label class="label">Nome</label>
            <input class="input" v-model="newDirectoryName" placeholder="Nome do diretório" />
          </div>
          <div class="modal-footer">
            <button @click="createDirectory" class="btn btn-primary">Criar</button>
            <button type="button" class="btn btn-secondary" @click="showCreateModal = false">Fechar</button>
          </div>
        </div>
      </div>
    </div>
     <!-- Modal para editar Diretório -->
    <div class="modal" v-if="showEditModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Editar Diretório</h5>
          </div>
          <div class="modal-body">
            <label class="label">Nome</label>
            <input class="input" v-model="editDirectoryName" placeholder="Novo nome do diretório" />
          </div>
          <div class="modal-footer">
            <button @click="updateDirectory" class="btn btn-primary">Salvar</button>
            <button type="button" class="btn btn-secondary" @click="showEditModal = false">Fechar</button>
          </div>
        </div>
      </div>
    </div>

       <!-- Modal para editar Arquivo -->
       <div class="modal" v-if="showEditFileModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Editar Arquivo</h5>
          </div>
          <div class="modal-body">
            <label class="label">Nome</label>
            <input class="input" v-model="editFileName" placeholder="Novo nome do arquivo" />
          </div>
          <div class="modal-footer">
            <button @click="updateFile" class="btn btn-primary">Salvar</button>
            <button type="button" class="btn btn-secondary" @click="showEditFileModal = false">Fechar</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal para Upload de Arquivo -->
    <div class="modal" v-if="showUploadModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Upload de Arquivo</h5>
          </div>
          <div class="modal-body">
            <input class="input" type="file" @change="handleFileChange" />
          </div>
          <div class="modal-footer">
            <button @click="uploadFile" class="btn btn-primary">Enviar</button>
            <button type="button" class="btn btn-secondary" @click="showUploadModal = false">Fechar</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal para Confirmação de Exclusão de Diretório -->
    <div class="modal" v-if="showConfirmDeleteModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Excluir Diretório</h5>
          </div>
          <div class="modal-body">
            <p>Tem certeza que deseja excluir este diretório?</p>
          </div>
          <div class="modal-footer">
            <button @click="deleteDirectory(editingDirectoryId)" class="btn btn-danger">Excluir</button>
            <button type="button" class="btn btn-secondary" @click="showConfirmDeleteModal = false">Cancelar</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal para Confirmação de Exclusão de Arquivo -->
    <div class="modal" v-if="showConfirmDeleteFileModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Excluir Arquivo</h5>
          </div>
          <div class="modal-body">
            <p>Tem certeza que deseja excluir este arquivo?</p>
          </div>
          <div class="modal-footer">
            <button @click="deleteFile(editingFileId)" class="btn btn-danger">Excluir</button>
            <button type="button" class="btn btn-secondary" @click="showConfirmDeleteFileModal = false">Cancelar</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      directories: [],
      selectedDirectory: null,
      files: [],
      newDirectoryName: '',
      editDirectoryName: '',
      showCreateModal: false,
      showEditModal: false,
      showEditFileModal: false,
      showUploadModal: false,
      showConfirmDeleteModal: false,
      showConfirmDeleteFileModal: false,
      editingDirectoryId: null,
      editingFileId: null,
      file: null,
    };
  },
  created() {
    this.fetchDirectories();
  },
  methods: {
    downloadFile(fileId) {
        const url = `http://localhost:8080/api/files/download/${fileId}`;
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', ''); // O download do arquivo será iniciado
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    },
    async fetchDirectories() {
      const response = await axios.get('http://localhost:8080/api/directories');
      this.directories = response.data;
    },
    selectDirectory(directory) {
      this.selectedDirectory = directory;
      this.fetchFiles(directory.id); // Chama a função para buscar os arquivos do diretório selecionado
    },
    async fetchFiles(directoryId) {
      const response = await axios.get(`http://localhost:8080/api/files/${directoryId}`);
      this.files = response.data;
    },
    async createDirectory() {
      await axios.post('http://localhost:8080/api/directories', { name: this.newDirectoryName });
      this.showCreateModal = false;
      this.newDirectoryName = '';
      this.fetchDirectories();
    },
    editDirectory(directory) {
      this.editingDirectoryId = directory.id;
      this.editDirectoryName = directory.name;
      this.showEditModal = true;
    },
    async updateDirectory() {
      await axios.put(`http://localhost:8080/api/directories/${this.editingDirectoryId}`, { name: this.editDirectoryName });
      this.showEditModal = false;
      this.editDirectoryName = '';
      this.fetchDirectories();
      this.fetchFiles(this.selectedDirectory.id); // Atualiza a lista de arquivos após editar
    },
    confirmDelete(id) {
      this.editingDirectoryId = id;
      this.showConfirmDeleteModal = true;
    },
    async deleteDirectory(id) {
      await axios.delete(`http://localhost:8080/api/directories/${id}`);
      this.showConfirmDeleteModal = false;
      this.fetchDirectories();
      this.fetchFiles(this.selectedDirectory.id); // Atualiza a lista de arquivos após exclusão
    },
    handleFileChange(event) {
      this.file = event.target.files[0];
    },
    async uploadFile() {
      const formData = new FormData();
      formData.append('file', this.file);
      formData.append('directoryId', this.selectedDirectory.id);
      await axios.post('http://localhost:8080/api/files/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      this.showUploadModal = false;
      this.fetchFiles(this.selectedDirectory.id); // Atualiza a lista de arquivos após upload
    },
    editFile(file) {
      this.editingFileId = file.id;
      this.editFileName = file.name; // Use uma variável para o nome do arquivo
      this.showEditFileModal = true; // Abra o modal de edição
    },
    async updateFile() {
      await axios.put(`http://localhost:8080/api/files/${this.editingFileId}`, { name: this.editFileName });
      this.showEditFileModal = false;
      this.editFileName = '';
      this.fetchFiles(this.selectedDirectory.id); // Atualiza a lista de arquivos após edição
    },
    confirmDeleteFile(id) {
      this.editingFileId = id;
      this.showConfirmDeleteFileModal = true;
    },
    async deleteFile(id) {
      await axios.delete(`http://localhost:8080/api/files/${id}`);
      this.showConfirmDeleteFileModal = false;
      this.fetchFiles(this.selectedDirectory.id); // Atualiza a lista de arquivos após exclusão
    },
  },
};
</script>

<style>

.container {
    display: flex;
}

.sidebar {
    display: flex;
    flex-direction: column; /* Alinha os itens verticalmente */
    padding: 10px;
}

.lista {
    display: flex;
    align-items: center; /* Alinha os itens verticalmente no centro */
    justify-content: space-between; /* Espaça os itens igualmente */
    margin-bottom: 10px; /* Espaço entre as linhas */
    padding: 5px; /* Espaçamento interno */
}

.directory-info {
    display: flex;
    justify-content: space-between; /* Para separar o nome dos botões */
    width: 100%; /* Faz o contêiner ocupar toda a largura */
}

.directory-name {
    flex-grow: 1; /* Permite que o nome do diretório cresça */
    margin-right: 20px; /* Espaço fixo entre o nome e os botões */
    overflow: hidden; /* Garante que o texto não ultrapasse os limites */
    white-space: nowrap; /* Impede que o texto quebre para a linha de baixo */
    text-overflow: ellipsis; /* Adiciona "..." ao final se o texto for muito longo */
}

.button-group {
    display: flex;
    gap: 10px; /* Espaço entre os botões */
}

.btn-editar, .btn-excluir {
    padding: 8px 12px; /* Ajuste o padding conforme necessário */
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.btn-editar {
    background-color: #4CAF50;
    color: white;
}

.btn-excluir {
    background-color: #f44336;
    color: white;
}


.container {
  display: flex;
  height: 100vh;
}

.sidebar {
  width: 30%;
  background-color: #f0f0f0;
  padding: 10px;
  overflow-y: auto;
}

.main-content {
  flex: 1;
  padding: 10px;
}

.selected {
  background-color: #d0e1f9;
}

.modal {
  display: block; /* Mostrar modal quando a condição for verdadeira */
  position: fixed;
  z-index: 1050;
  left: 0;
  top: 0;
  width: 100%;
  min-height: 800px;
  overflow: hidden;
  background-color: rgba(0, 0, 0, 0.5); /* Fundo semi-transparente */
}

.modal-dialog {
  max-width: 800px; /* Definindo uma largura máxima para o modal */
  margin: 30px auto; /* Centralizando o modal */
  min-height: 800px;
}

.modal-content {
  background-color: white; /* Fundo branco para o conteúdo do modal */
  border-radius: 0.5rem; /* Bordas arredondadas */
  box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15); /* Sombra suave */
}

.modal-header, .modal-footer {
  border: none; /* Remover bordas superiores e inferiores */
  margin: 10px;
  padding: 20px;
}

.modal-title {
  margin: 0; /* Remover margens do título */
  font-size: 1.5rem;
}

.label {
  margin: 10px;
  font-weight: bold; /* Negrito para destaque */
  color: #333; /* Cor do texto */
}

.input {
  margin: 30px;
  border-radius: 0.5rem; /* Bordas arredondadas */
  border: 1px solid #333; /* Bordas padrão */
  padding: 15px; /* Espaçamento interno */
  width: calc(80% - 40px); /* Ajuste para ocupar toda a largura */
}

.input:focus {
  border-color: #007bff; /* Cor da borda ao focar */
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5); /* Sombra suave ao focar */
}

.btn {
  border-radius: 0.5rem; /* Bordas arredondadas */
  padding: 10px 20px; /* Espaçamento interno */
  margin: 10px; /* Margem */
  color: white; /* Cor do texto */
  background-color: #007bff; /* Cor de fundo do botão */
  border: none; /* Remover borda padrão */
  transition: background-color 0.3s; /* Transição suave para mudança de cor */
}

.btn-editar {
  border-radius: 0.5rem; /* Bordas arredondadas */
  padding: 5px 10px; /* Espaçamento interno */
  margin: 10px; /* Margem */
  color: white; /* Cor do texto */
  background-color: #007bff; /* Cor de fundo do botão */
  border: none; /* Remover borda padrão */
  transition: background-color 0.3s; /* Transição suave para mudança de cor */
  cursor: pointer;
}

.btn-excluir {
  border-radius: 0.5rem; /* Bordas arredondadas */
  padding: 5px 10px; /* Espaçamento interno */
  margin: 10px; /* Margem */
  color: white; /* Cor do texto */
  background-color: red; /* Cor de fundo do botão */
  border: none; /* Remover borda padrão */
  transition: background-color 0.3s; /* Transição suave para mudança de cor */
  cursor: pointer;
}

.btn-download {
  border-radius: 0.5rem; /* Bordas arredondadas */
  padding: 5px 10px; /* Espaçamento interno */
  margin: 10px; /* Margem */
  color: white; /* Cor do texto */
  background-color: #5a6268; /* Cor de fundo do botão */
  border: none; /* Remover borda padrão */
  transition: background-color 0.3s; /* Transição suave para mudança de cor */
  cursor: pointer;
}

.btn:hover {
  background-color: #0056b3;
  cursor: pointer; /* Cor do botão ao passar o mouse */
}

.btn-secondary {
  background-color: #6c757d;
  cursor: pointer; /* Cor de fundo para botões secundários */
  padding: 10px 18px;
}

.btn-secondary:hover {
  background-color: #5a6268;
  cursor: pointer; /* Cor ao passar o mouse para botões secundários */
}

.lista{
  cursor: pointer;
}

.tabela {
            width: 100%;
            margin: 20px 0;
            border-collapse: collapse; /* Remove espaçamento entre células */
        }
        .tabela th, .tabela td {
            padding: 15px;
            text-align: center;
            border: 1px solid #ddd; /* Borda para separação de colunas */
        }
        .tabela th {
            background-color: #f2f2f2; /* Cor de fundo dos cabeçalhos */
        }
        @media (max-width: 768px) {
            .tabela {
                font-size: 14px; /* Tamanho de fonte reduzido em telas menores */
            }
        }
        body {
            margin: 0; /* Remove a margem padrão do body */
            font-family: Arial, sans-serif;
        }
        .navbar {
          margin: 0;
            background-color: #007bff; /* Cor de fundo */
            padding: 30px 20px; /* Espaçamento interno */
            display: flex; /* Usar Flexbox */
            justify-content: space-between; /* Espaçar itens */
            align-items: center; /* Centralizar verticalmente */
        }

        .navbar-brand {
            color: #fff; /* Cor do texto da marca */
            font-weight: bold; /* Negrito */
            text-decoration: none; /* Remover sublinhado */
        }

        .navbar-nav {
            display: flex; /* Flex para itens da nav */
            list-style-type: none; /* Remover marcador da lista */
            padding: 0; /* Remover padding */
            margin: 0; /* Remover margin */
        }

        .nav-item {
            margin-left: 20px; /* Espaçamento entre itens */
        }

        .nav-link {
            color: #fff; /* Cor dos links */
            text-decoration: none; /* Remover sublinhado */
            transition: color 0.3s; /* Transição suave */
        }

        .nav-link:hover {
            color: #ffeb3b; /* Cor ao passar o mouse */
        }


</style>

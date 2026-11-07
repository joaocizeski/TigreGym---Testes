import { Aluno } from "../types/aluno";
import api from "./api";



export async function buscarListaAlunos() : Promise<Aluno[]> {

    const dados = await api.get<Aluno[]>("/alunos");

    if(dados.status == 200){
        return dados.data;
    }

    return [];
    
}


export async function deletarAluno(id:number) : Promise<number>{

    var dados = await api.delete('/alunos/'+id+'/excluir');
    return dados.status;
}


export async function atualizarStatusAluno(aluno:Aluno) :Promise<void>{
   
    var novoStatus = {};

    if(aluno.status ==="ATIVO"){
        novoStatus = {status:"BLOQUEADO"}
    }else{
        novoStatus = {status:"ATIVO"}
    }

    var dadosRetorno = await  
    api.patch('/alunos/'+aluno.id+'/status',novoStatus);

    if(dadosRetorno.status==200){
        alert("Atulizado status com sucesso!");
    }else{
        alert(dadosRetorno.data);

        return;
    } 
}


export async function consultaAlunoPorId(id:number):Promise<Aluno>{

    const valorAlunoBack = await api.get<Aluno>('/alunos/'+id);

    if (!valorAlunoBack.data.numero) {
        valorAlunoBack.data.numero = "S/N";
    }

    if (!valorAlunoBack.data.complemento) {
        valorAlunoBack.data.complemento = "";
    }

    return valorAlunoBack.data;
}


export async function atualizarAluno(aluno:Aluno): Promise<Aluno>{

    var dadosRetorno = await  
    api.put<Aluno>('/alunos/'+aluno.id,aluno);

    return dadosRetorno.data;
}


export async function salvarAluno(aluno:Aluno): Promise<Aluno>{

    var dadosRetorno = await  
    api.post<Aluno>('/alunos',aluno);

    return dadosRetorno.data;
}
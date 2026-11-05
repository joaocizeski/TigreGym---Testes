"use client";

import { atualizarStatusUsuario, buscarListaUsuarios, deletarUsuario } from "@/app/services/usuarioService";
import { Usuario } from "@/app/types/usuario";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function Usuarios(){

  const [usuarios,setUsuarios] = useState<Usuario[]>([]);

  useEffect(()=>{
      carregarDados();
  },[]);

  const carregarDados = async ()=>{

      try {
          const dados = await buscarListaUsuarios();

          setUsuarios( dados);

      } catch (error) {
          alert("Erro ao carregar dados!")
      }
     

  }

  const handleDeletarUsuario = async(usuario:Usuario) =>{

      var dadosRetorno = await  deletarUsuario(usuario.id || 0);
     
      if(dadosRetorno==200){
          alert("Excluido com sucesso!");
      }else{
          alert("Erro ao excluir usuário!");

          return;
      }

      carregarDados();

  }

  const handleAlterarStatusUsuario = async(usuario:Usuario) =>{

      await atualizarStatusUsuario(usuario);

      carregarDados();

  }

  return (
    <div className="w-full bg-[#070707] px-6 py-8 md:px-10">
      <div className="w-full flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 mb-8">
        <div>
          <p className="text-yellow-400 text-sm font-bold tracking-[3px] mb-2">
            TIGREGYM
          </p>

          <h1 className="text-3xl md:text-4xl font-black text-white tracking-tight">
            Gestão de usuários
          </h1>

          <p className="text-zinc-500 mt-2">Usuários cadastrados no sistema</p>
        </div>

        <Link
          href="/usuario/novo"
          className="inline-flex items-center justify-center px-5 py-3 bg-yellow-400 hover:bg-yellow-300 text-black font-bold text-sm rounded-lg transition-colors duration-150"
        >
          + Novo Usuário
        </Link>
      </div>

      <div className="w-full">
        <div className="bg-zinc-950 border border-zinc-800 rounded-xl shadow-2xl overflow-hidden w-full">
          <div className="overflow-x-auto w-full">
            <table className="w-full text-left border-collapse">
              <thead>
                <tr className="bg-zinc-900 border-b border-zinc-800">
                  <th className="px-6 py-4 text-xs font-bold text-zinc-400 uppercase tracking-wider">
                    Código
                  </th>

                  <th className="px-6 py-4 text-xs font-bold text-zinc-400 uppercase tracking-wider">
                    Nome
                  </th>

                  <th className="px-6 py-4 text-xs font-bold text-zinc-400 uppercase tracking-wider">
                    CPF
                  </th>

                  <th className="px-6 py-4 text-xs font-bold text-zinc-400 uppercase tracking-wider">
                    E-mail
                  </th>

                  <th className="px-6 py-4 text-xs font-bold text-zinc-400 uppercase tracking-wider">
                    Status
                  </th>

                  <th className="px-6 py-4 text-xs font-bold text-zinc-400 uppercase tracking-wider">
                    Ações
                  </th>
                </tr>
              </thead>

              <tbody className="divide-y divide-zinc-800">
                {usuarios.map((usuario) => (
                  <tr
                    key={usuario.id}
                    className="hover:bg-zinc-900/80 transition-colors duration-150"
                  >
                    <td className="px-6 py-5 text-sm font-medium text-zinc-500">
                      {usuario.id}
                    </td>

                    <td className="px-6 py-5 text-sm font-bold text-white">
                      {usuario.nome}
                    </td>

                    <td className="px-6 py-5 text-sm font-medium text-zinc-400">
                      {usuario.cpf}
                    </td>

                    <td className="px-6 py-5 text-sm font-medium text-zinc-400">
                      {usuario.email}
                    </td>

                    <td className="px-6 py-5 text-sm font-medium">
                      <span className={`inline-flex px-3 py-1 rounded-full border text-xs font-bold ${
                        usuario.status === "ATIVO"
                          ? "bg-green-500/15 border-green-500/30 text-green-400"
                          : usuario.status === "BLOQUEADO"
                            ? "bg-[#F5C518]/15 border-[#F5C518]/30 text-[#F5C518]"
                            : "bg-red-500/15 border-red-500/30 text-red-400"
                      }`}>
                        {usuario.status}
                      </span>
                    </td>

                    <td className="px-6 py-5 text-sm font-medium">
                      <div className="flex items-center gap-4">
                        <Link
                          href={"/usuario/" + usuario.id + "/editar"}
                          className="text-yellow-400 hover:text-yellow-300"
                        >
                          Editar
                        </Link>

                        <button
                          onClick={() => handleDeletarUsuario(usuario)}
                          className="text-red-500 hover:text-red-400"
                        >
                          Excluir
                        </button>

                        <button
                          onClick={() => handleAlterarStatusUsuario(usuario)}
                          className={
                            usuario.status === "BLOQUEADO"
                              ? "text-orange-400 hover:text-orange-300"
                              : "text-green-400 hover:text-green-300"
                          }
                        >
                          {usuario.status}
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}

                {usuarios.length === 0 && (
                  <tr>
                    <td
                      colSpan={6}
                      className="px-6 py-14 text-center text-zinc-500 italic"
                    >
                      Nenhum usuário encontrado!
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
}

"use client";

import { atualizarStatusAluno, buscarListaAlunos, deletarAluno } from "@/app/services/alunoService";
import Link from "next/link";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../redux/store";
import { setAlunos } from "../../redux/slices/alunosSlice";
import { Aluno } from "../../types/aluno";

export default function Alunos() {
  const alunos = useSelector((state: RootState) => state.alunos.lista);
  const dispatch = useDispatch();

  // Quando a página abrir, carrega os dados cadastrados
  useEffect(() => {
    carregarDados();
  }, []);

  // Busca no backend a lista de alunos
  const carregarDados = async () => {
    try {
      const dados = await buscarListaAlunos();

      // Guarda a lista de alunos no Redux
      dispatch(setAlunos(dados));
    } catch (error) {
      console.log(error);
      alert("Erro ao carregar dados!");
    }
  };

  // Faz a exclusão sem apagar o aluno do banco
  const handleDeletarAluno = async (aluno: Aluno) => {
    var dadosRetorno = await deletarAluno(aluno.id || 0);

    if (dadosRetorno == 200) {
      alert("Excluído com sucesso!");
    } else {
      alert("Erro ao excluir aluno!");
      return;
    }

    carregarDados();
  };

  // Troca o status entre ATIVO e BLOQUEADO
  const handleAlterarStatusAluno = async (aluno: Aluno) => {
    await atualizarStatusAluno(aluno);

    carregarDados();
  };

  return (
    <div className="w-full bg-[#070707] text-white px-6 py-10 md:px-10">
      <div className="max-w-7xl mx-auto">
        <div className="flex flex-col md:flex-row md:items-end md:justify-between gap-5 mb-10">
          <div>
            <p className="text-[#F5C518] text-sm font-bold tracking-[3px] mb-3">
              TIGREGYM
            </p>

            <h1 className="text-4xl md:text-5xl font-black">Alunos</h1>

            <p className="text-zinc-400 mt-3">
              Gerencie os alunos cadastrados na academia.
            </p>
          </div>

          <Link
            href="/aluno/novo"
            className="bg-[#F5C518] hover:bg-yellow-300 text-black font-bold px-6 py-3 rounded-lg transition"
          >
            + Novo Aluno
          </Link>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-8">
          <div className="bg-zinc-950 border border-zinc-800 rounded-xl p-5">
            <p className="text-zinc-500 text-sm">Total de alunos</p>

            <p className="text-3xl font-black mt-2">{alunos.length}</p>
          </div>

          <div className="bg-zinc-950 border border-zinc-800 rounded-xl p-5">
            <p className="text-zinc-500 text-sm">Alunos ativos</p>

            <p className="text-3xl font-black text-green-400 mt-2">
              {alunos.filter((aluno) => aluno.status == "ATIVO").length}
            </p>
          </div>

          <div className="bg-zinc-950 border border-zinc-800 rounded-xl p-5">
            <p className="text-zinc-500 text-sm">Bloqueados</p>

            <p className="text-3xl font-black text-red-400 mt-2">
              {alunos.filter((aluno) => aluno.status == "BLOQUEADO").length}
            </p>
          </div>
        </div>

        <div className="bg-zinc-950 border border-zinc-800 rounded-2xl overflow-x-auto">
          <table className="w-full text-left min-w-[1100px]">
            <thead className="bg-zinc-900/80">
              <tr className="text-zinc-400 text-sm">
                <th className="p-5">Código</th>
                <th className="p-5">Nome</th>
                <th className="p-5">CPF</th>
                <th className="p-5">Nascimento</th>
                <th className="p-5">Telefone</th>
                <th className="p-5">E-mail</th>
                <th className="p-5">Status</th>
                <th className="p-5">Ações</th>
              </tr>
            </thead>

            <tbody>
              {/* Percorre a lista de alunos e cria uma linha para cada item */}
              {alunos.map((aluno) => (
                <tr
                  key={aluno.id}
                  className="border-t border-zinc-800 hover:bg-zinc-900/40"
                >
                  <td className="p-5 text-zinc-500">{aluno.id}</td>

                  <td className="p-5 font-semibold">{aluno.nome}</td>

                  <td className="p-5 text-zinc-400">{aluno.cpf}</td>

                  <td className="p-5 text-zinc-400">{aluno.dataNascimento}</td>

                  <td className="p-5 text-zinc-400">{aluno.telefone}</td>

                  <td className="p-5 text-zinc-400">{aluno.email}</td>

                  <td className="p-5">
                    <span className={`inline-flex px-3 py-1 rounded-full border text-xs font-bold ${
                      aluno.status === "ATIVO"
                        ? "bg-green-500/15 border-green-500/30 text-green-400"
                        : aluno.status === "BLOQUEADO"
                          ? "bg-[#F5C518]/15 border-[#F5C518]/30 text-[#F5C518]"
                          : "bg-red-500/15 border-red-500/30 text-red-400"
                    }`}>
                      {aluno.status}
                    </span>
                  </td>

                  <td className="p-5">
                    <div className="flex items-center gap-4">
                      <Link
                        href={"/aluno/" + aluno.id + "/editar"}
                        className="text-yellow-400 hover:text-yellow-300"
                      >
                        Editar
                      </Link>

                      <button
                        onClick={() => handleDeletarAluno(aluno)}
                        className="text-red-500 hover:text-red-400"
                      >
                        Excluir
                      </button>

                      <button
                        onClick={() => handleAlterarStatusAluno(aluno)}
                        className={
                          aluno.status === "BLOQUEADO"
                            ? "text-orange-400 hover:text-orange-300"
                            : "text-green-400 hover:text-green-300"
                        }
                      >
                        {aluno.status}
                      </button>
                    </div>
                  </td>
                </tr>
              ))}

              {alunos.length === 0 && (
                <tr>
                  <td colSpan={8} className="p-10 text-center text-zinc-500">
                    Nenhum aluno encontrado!
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

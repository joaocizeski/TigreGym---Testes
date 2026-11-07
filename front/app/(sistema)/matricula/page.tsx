"use client";

import { atualizarStatusMatricula, buscarListaMatriculas, deletarMatricula } from "@/app/services/matriculaService";
import Link from "next/link";
import { useEffect, useState } from "react";
import { Matricula } from "../../types/matricula";

export default function Matriculas() {
  // Guarda a lista de matrículas que será mostrada na tela
  const [matriculas, setMatriculas] = useState<Matricula[]>([]);

  // Quando a página abrir, carrega os dados cadastrados
  useEffect(() => {
    carregarDados();
  }, []);

  // Busca no backend a lista de matrículas
  const carregarDados = async () => {
    try {
      const dados = await buscarListaMatriculas();

      // Guarda as matrículas recebidas para atualizar a tela
      setMatriculas(dados);
    } catch (error) {
      alert("Erro ao carregar dados!");
    }
  };

  // Faz a exclusão sem apagar a matrícula do banco
  const handleDeletarMatricula = async (matricula: Matricula) => {
    var dadosRetorno = await deletarMatricula(matricula.id || 0);

    if (dadosRetorno == 200) {
      alert("Excluído com sucesso!");
    } else {
      alert("Erro ao excluir matrícula!");
      return;
    }

    carregarDados();
  };

  // Troca o status entre ATIVO e BLOQUEADO
  const handleAlterarStatusMatricula = async (matricula: Matricula) => {
    await atualizarStatusMatricula(matricula);

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

            <h1 className="text-4xl md:text-5xl font-black">Matrículas</h1>

            <p className="text-zinc-400 mt-3">
              Acompanhe e gerencie as matrículas dos alunos.
            </p>
          </div>

          <Link
            href="/matricula/novo"
            className="bg-[#F5C518] hover:bg-yellow-300 text-black font-bold px-6 py-3 rounded-lg transition"
          >
            + Nova Matrícula
          </Link>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-8">
          <div className="bg-zinc-950 border border-zinc-800 rounded-xl p-5">
            <p className="text-zinc-500 text-sm">Total de matrículas</p>

            <p className="text-3xl font-black mt-2">{matriculas.length}</p>
          </div>

          <div className="bg-zinc-950 border border-zinc-800 rounded-xl p-5">
            <p className="text-zinc-500 text-sm">Ativas</p>

            <p className="text-3xl font-black text-green-400 mt-2">
              {
                matriculas.filter((matricula) => matricula.status == "ATIVO")
                  .length
              }
            </p>
          </div>

          <div className="bg-zinc-950 border border-zinc-800 rounded-xl p-5">
            <p className="text-zinc-500 text-sm">Bloqueadas</p>

            <p className="text-3xl font-black text-red-400 mt-2">
              {
                matriculas.filter(
                  (matricula) => matricula.status == "BLOQUEADO"
                ).length
              }
            </p>
          </div>
        </div>

        <div className="bg-zinc-950 border border-zinc-800 rounded-2xl overflow-x-auto">
          <table className="w-full text-left min-w-[1000px]">
            <thead className="bg-zinc-900/80">
              <tr className="text-zinc-400 text-sm">
                <th className="p-5">Código</th>
                <th className="p-5">Aluno</th>
                <th className="p-5">Plano</th>
                <th className="p-5">Início</th>
                <th className="p-5">Vencimento</th>
                <th className="p-5">Status</th>
                <th className="p-5">Ações</th>
              </tr>
            </thead>

            <tbody>
              {matriculas.map((matricula) => (
                <tr
                  key={matricula.id}
                  className={
                    matricula.status === "EXCLUIDO"
                      ? "border-t border-red-900/40 bg-red-950/20 opacity-60"
                      : "border-t border-zinc-800 hover:bg-zinc-900/40"
                  }
                >
                  <td className="p-5 text-zinc-500">{matricula.id}</td>

                  <td className="p-5 font-semibold">{matricula.aluno}</td>

                  <td className="p-5 text-zinc-400">{matricula.plano}</td>

                  {/* Converte as datas para o formato brasileiro somente na exibição */}
                  <td className="p-5 text-zinc-400">{new Date(matricula.dataInicio).toLocaleString("pt-BR")}</td>

                  <td className="p-5 text-zinc-400">
                    {new Date(matricula.dataVencimento).toLocaleString("pt-BR")}
                  </td>

                  <td className="p-5">
                    <span className={`inline-flex px-3 py-1 rounded-full border text-xs font-bold ${
                      matricula.status === "ATIVO"
                        ? "bg-green-500/15 border-green-500/30 text-green-400"
                        : matricula.status === "BLOQUEADO"
                          ? "bg-[#F5C518]/15 border-[#F5C518]/30 text-[#F5C518]"
                          : "bg-red-500/15 border-red-500/30 text-red-400"
                    }`}>
                      {matricula.status}
                    </span>
                  </td>

                  <td className="p-5">
                    {matricula.status === "EXCLUIDO" ? (
                      <span className="text-red-400 font-bold">
                        Registro excluído
                      </span>
                    ) : (
                      <div className="flex items-center gap-4">
                        <Link
                          href={"/matricula/" + matricula.id + "/editar"}
                          className="text-yellow-400 hover:text-yellow-300"
                        >
                          Editar
                        </Link>

                        <button
                          onClick={() => handleDeletarMatricula(matricula)}
                          className="text-red-500 hover:text-red-400"
                        >
                          Excluir
                        </button>

                        <button
                          onClick={() => handleAlterarStatusMatricula(matricula)}
                          className={
                            matricula.status === "BLOQUEADO"
                              ? "text-orange-400 hover:text-orange-300"
                              : "text-green-400 hover:text-green-300"
                          }
                        >
                          {matricula.status}
                        </button>
                      </div>
                    )}
                  </td>
                </tr>
              ))}

              {matriculas.length === 0 && (
                <tr>
                  <td colSpan={7} className="p-10 text-center text-zinc-500">
                    Nenhuma matrícula encontrada!
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

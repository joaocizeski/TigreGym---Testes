"use client";

import { atualizarStatusPlano, buscarListaPlanos, deletarPlano } from "@/app/services/planoService";
import Link from "next/link";
import { useEffect, useState } from "react";
import { Plano } from "../../types/plano";

export default function Planos() {
  // Guarda a lista de planos que será mostrada na tela
  const [planos, setPlanos] = useState<Plano[]>([]);

  // Quando a página abrir, carrega os dados cadastrados
  useEffect(() => {
    carregarDados();
  }, []);

  // Busca no backend a lista de planos
  const carregarDados = async () => {
    try {
      const dados = await buscarListaPlanos();

      // Guarda os planos recebidos para atualizar a tela
      setPlanos(dados);
    } catch (error) {
      alert("Erro ao carregar dados!");
    }
  };

  // Faz a exclusão sem apagar o plano do banco
  const handleDeletarPlano = async (plano: Plano) => {
    var dadosRetorno = await deletarPlano(plano.id || 0);

    if (dadosRetorno == 200) {
      alert("Excluído com sucesso!");
    } else {
      alert("Erro ao excluir plano!");
      return;
    }

    carregarDados();
  };

  // Troca o status entre ATIVO e BLOQUEADO
  const handleAlterarStatusPlano = async (plano: Plano) => {
    await atualizarStatusPlano(plano);

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

            <h1 className="text-4xl md:text-5xl font-black">Planos</h1>

            <p className="text-zinc-400 mt-3">
              Gerencie os planos disponíveis na academia.
            </p>
          </div>

          <Link
            href="/plano/novo"
            className="bg-[#F5C518] hover:bg-yellow-300 text-black font-bold px-6 py-3 rounded-lg transition"
          >
            + Novo Plano
          </Link>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
          {planos.map((plano) => (
            <div
              key={plano.id}
              className={
                plano.status === "EXCLUIDO"
                  ? "bg-red-950/20 border border-red-900/40 rounded-2xl p-7 opacity-60"
                  : "bg-zinc-950 border border-zinc-800 rounded-2xl p-7 hover:border-yellow-400 transition"
              }
            >
              <div className="flex items-start justify-between">
                <div className="w-12 h-12 bg-zinc-900 rounded-xl flex items-center justify-center text-2xl">
                  🏋️
                </div>

                <span className={`border px-3 py-1 rounded-full text-xs font-bold ${
                  plano.status === "ATIVO"
                    ? "bg-green-500/15 border-green-500/30 text-green-400"
                    : plano.status === "BLOQUEADO"
                      ? "bg-[#F5C518]/15 border-[#F5C518]/30 text-[#F5C518]"
                      : "bg-red-500/15 border-red-500/30 text-red-400"
                }`}>
                  {plano.status}
                </span>
              </div>

              <div className="mt-7">
                <p className="text-xs text-zinc-500 font-bold tracking-widest">
                  PLANO
                </p>

                <h2 className="text-2xl font-bold mt-2">{plano.nome}</h2>

                <p className="text-zinc-400 mt-3 min-h-12">{plano.descricao}</p>
              </div>

              <div className="border-t border-zinc-800 mt-6 pt-6">
                <span className="text-4xl font-black text-[#F5C518]">
                  R$ {plano.valor}
                </span>
              </div>

              <div className="flex items-center justify-between mt-6 text-sm">
                <span className="text-zinc-500">Duração</span>

                <span className="font-semibold">
                  {plano.duracaoEmMeses
                    ? `${plano.duracaoEmMeses} ${
                        plano.duracaoEmMeses === 1 ? "mês" : "meses"
                      }`
                    : "Não definida"}
                </span>
              </div>

              <div className="flex items-center gap-4 mt-6 pt-5 border-t border-zinc-800">
                {plano.status === "EXCLUIDO" ? (
                  <span className="text-red-400 font-bold">
                    Registro excluído
                  </span>
                ) : (
                  <>
                    <Link
                      href={"/plano/" + plano.id + "/editar"}
                      className="text-yellow-400 hover:text-yellow-300"
                    >
                      Editar
                    </Link>

                    <button
                      onClick={() => handleDeletarPlano(plano)}
                      className="text-red-500 hover:text-red-400"
                    >
                      Excluir
                    </button>

                    <button
                      onClick={() => handleAlterarStatusPlano(plano)}
                      className={
                        plano.status === "BLOQUEADO"
                          ? "text-orange-400 hover:text-orange-300"
                          : "text-green-400 hover:text-green-300"
                      }
                    >
                      {plano.status}
                    </button>
                  </>
                )}
              </div>
            </div>
          ))}

          {planos.length === 0 && (
            <div className="col-span-full text-center text-zinc-500 py-16">
              Nenhum plano encontrado!
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

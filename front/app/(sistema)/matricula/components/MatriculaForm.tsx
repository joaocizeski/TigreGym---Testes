"use client";

import Link from "next/link";
import { useEffect, useState } from "react";
import { Matricula, MatriculaFormProps } from "../../../types/matricula";
import { Aluno } from "../../../types/aluno";
import { Plano } from "../../../types/plano";
import { buscarListaAlunos } from "@/app/services/alunoService";
import { buscarListaPlanos } from "@/app/services/planoService";
import { atualizarMatricula, salvarMatricula } from "@/app/services/matriculaService";
import { useRouter } from "next/navigation";

export default function UsuarioForm({
  matriculaExistente,
}: MatriculaFormProps) {
  const router = useRouter();

  const [alunos, setAlunos] = useState<Aluno[]>([]);
  const [planos, setPlanos] = useState<Plano[]>([]);

  // Usa os dados existentes quando for edição
  const [matricula, setMatricula] = useState<Matricula>(
    matriculaExistente ||
      new Matricula(null, null, "", null, "", "", "", "ATIVO")
  );

  useEffect(() => {
    carregarOpcoes();
  }, []);

  // Busca os alunos e planos já cadastrados no banco
  const carregarOpcoes = async () => {
    try {
      const [dadosAlunos, dadosPlanos] = await Promise.all([
        buscarListaAlunos(),
        buscarListaPlanos(),
      ]);

      setAlunos(dadosAlunos);
      setPlanos(dadosPlanos);
    } catch (error) {
      alert("Erro ao carregar alunos e planos!");
    }
  };

  // Muda somente o aluno ou plano escolhido
  const handlerChange = (
    campo: "alunoId" | "planoId",
    valor: string
  ) => {
    setMatricula(
      (valorAnterior) =>
        new Matricula(
          valorAnterior.id,
          campo === "alunoId" ? Number(valor) : valorAnterior.alunoId,
          valorAnterior.aluno,
          campo === "planoId" ? Number(valor) : valorAnterior.planoId,
          valorAnterior.plano,
          valorAnterior.dataInicio,
          valorAnterior.dataVencimento,
          valorAnterior.status
        )
    );
  };

  const handlerSalvar = async () => {
    if (matriculaExistente) {
      const dadosRetorno = await atualizarMatricula(matricula);

      if (dadosRetorno) {
        alert("Matrícula foi salva com sucesso!");
      } else {
        alert("Erro ao atualizar matrícula!");
        return;
      }
    } else {
      const dadosRetorno = await salvarMatricula(matricula);

      if (dadosRetorno) {
        alert("Matrícula foi salva com sucesso!");
      } else {
        alert("Erro ao salvar matrícula!");
        return;
      }
    }

    router.push("/matricula");
  };

  return (
    <form action={handlerSalvar} className="w-full">
      <div className="bg-zinc-950 border border-zinc-800 rounded-2xl p-8 shadow-2xl">
        <div className="mb-8">
          <p className="text-yellow-400 text-sm font-bold tracking-[3px]">
            TIGREGYM
          </p>

          <h2 className="text-3xl font-black text-white mt-2">
            Cadastro de matrícula
          </h2>

          <p className="text-zinc-500 mt-2">
            Selecione o aluno e o plano. As datas são calculadas automaticamente.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
          <div>
            <label className="block text-sm font-semibold text-zinc-300 mb-2">
              Aluno:
            </label>

            <select
              name="aluno"
              value={matricula.alunoId ?? ""}
              required
              onChange={(e) => handlerChange("alunoId", e.target.value)}
              className="w-full bg-zinc-900 border border-zinc-800 rounded-lg px-4 py-3 text-white outline-none focus:border-yellow-400 transition"
            >
              <option value="">Selecione um aluno</option>

              {alunos.map((aluno) => (
                <option key={aluno.id} value={aluno.id ?? ""}>
                  {aluno.nome}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="block text-sm font-semibold text-zinc-300 mb-2">
              Plano:
            </label>

            <select
              name="plano"
              value={matricula.planoId ?? ""}
              required
              onChange={(e) => handlerChange("planoId", e.target.value)}
              className="w-full bg-zinc-900 border border-zinc-800 rounded-lg px-4 py-3 text-white outline-none focus:border-yellow-400 transition"
            >
              <option value="">Selecione um plano</option>

              {planos.map((plano) => (
                <option key={plano.id} value={plano.id ?? ""}>
                  {plano.nome}
                  {plano.duracaoEmMeses
                    ? ` - ${plano.duracaoEmMeses} ${
                        plano.duracaoEmMeses === 1 ? "mês" : "meses"
                      }`
                    : ""}
                </option>
              ))}
            </select>
          </div>
        </div>

        <div className="mt-5 rounded-lg border border-zinc-800 bg-zinc-900/50 p-4">
          <p className="text-sm text-zinc-400">
            A matrícula começa no momento do cadastro e vence conforme a duração do plano selecionado.
          </p>
        </div>

        <div className="flex justify-end gap-3 mt-8">
          <Link
            href="/matricula"
            className="px-5 py-3 rounded-lg border border-zinc-700 text-zinc-300 hover:bg-zinc-900 transition"
          >
            Cancelar
          </Link>

          <button
            type="submit"
            className="px-6 py-3 rounded-lg bg-yellow-400 text-black font-bold hover:bg-yellow-300 transition"
          >
            Salvar
          </button>
        </div>
      </div>
    </form>
  );
}

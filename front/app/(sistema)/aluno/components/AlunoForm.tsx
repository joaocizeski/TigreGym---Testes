"use client";

import Link from "next/link";
import { useState } from "react";
import { Aluno, AlunoFormProps } from "../../../types/aluno";
import { atualizarAluno, salvarAluno } from "@/app/services/alunoService";
import { buscarCep } from "@/app/services/cepService";
import { useRouter } from "next/navigation";
import axios from "axios";

export default function AlunoForm({ alunoExistente }: AlunoFormProps) {
  const router = useRouter();
  const [enderecoBuscado, setEnderecoBuscado] = useState<boolean>(!!alunoExistente?.cep);

  const [aluno, setAluno] = useState<Aluno>(
    alunoExistente ||
      new Aluno(null, "", "", "", "", "", "", "", "S/N", "", "", "", "", "", "ATIVO")
  );

  const handlerChange = (
    campo:
      | "nome"
      | "cpf"
      | "dataNascimento"
      | "telefone"
      | "email"
      | "cep"
      | "logradouro"
      | "numero"
      | "complemento"
      | "bairro"
      | "cidade"
      | "uf",
    valor: string
  ) => {

    if (campo === "numero") {
      valor = valor.replace(/\D/g, "");
    }

    if (campo === "cep") {
      setEnderecoBuscado(false);
    }

    setAluno(
      (valorAnterior) =>
        new Aluno(
          valorAnterior.id,
          campo === "nome" ? valor : valorAnterior.nome,
          campo === "cpf" ? valor : valorAnterior.cpf,
          campo === "dataNascimento" ? valor : valorAnterior.dataNascimento,
          campo === "telefone" ? valor : valorAnterior.telefone,
          campo === "email" ? valor : valorAnterior.email,
          campo === "cep" ? valor : valorAnterior.cep,
          campo === "logradouro" ? valor : valorAnterior.logradouro,
          campo === "numero" ? valor : valorAnterior.numero,
          campo === "complemento" ? valor : valorAnterior.complemento,
          campo === "bairro" ? valor : valorAnterior.bairro,
          campo === "cidade" ? valor : valorAnterior.cidade,
          campo === "uf" ? valor : valorAnterior.uf,
          valorAnterior.status
        )
    );
  };

  const handlerBuscarCep = async () => {
    try {
      if (!aluno.cep || aluno.cep.replace(/\D/g, "").length !== 8) {
        alert("Informe um CEP com 8 números!");
        return;
      }

      const endereco = await buscarCep(aluno.cep);

      setAluno(
        (valorAnterior) =>
          new Aluno(
            valorAnterior.id,
            valorAnterior.nome,
            valorAnterior.cpf,
            valorAnterior.dataNascimento,
            valorAnterior.telefone,
            valorAnterior.email,
            endereco.cep || valorAnterior.cep,
            endereco.logradouro || "",
            valorAnterior.numero,
            valorAnterior.complemento,
            endereco.bairro || "",
            endereco.cidade || "",
            endereco.uf || "",
            valorAnterior.status
          )
      );

      setEnderecoBuscado(true);

    } catch (error) {
      if (axios.isAxiosError(error) && error.response?.data) {
        alert(error.response.data);
      } else {
        alert("Erro ao consultar CEP!");
      }
    }
  };

  const handlerSalvar = async () => {
    try {

      if (alunoExistente) {

        const dadosRetorno = await atualizarAluno(aluno);

        if (dadosRetorno) {
          alert("Aluno foi salvo com sucesso!");
        } else {
          alert("Erro ao atualizar aluno!");
          return;
        }

      } else {

        const dadosRetorno = await salvarAluno(aluno);

        if (dadosRetorno) {
          alert("Aluno foi salvo com sucesso!");
        } else {
          alert("Erro ao salvar aluno!");
          return;
        }

      }

      router.push("/aluno");

    } catch (error) {

      if (axios.isAxiosError(error)) {

        const mensagem = error.response?.data;

        if (mensagem) {
          alert(mensagem);
        } else {
          alert("Erro ao salvar aluno!");
        }

      } else {
        alert("Erro ao salvar aluno!");
      }

    }
  };

  return (
    <form action={handlerSalvar} className="w-full">
      <div className="bg-zinc-950 border border-zinc-800 rounded-2xl p-8 shadow-2xl">
        <div className="mb-8">
          <p className="text-yellow-400 text-sm font-bold tracking-[3px]">
            TIGREGYM
          </p>

          <h2 className="text-3xl font-black text-white mt-2">
            Cadastro de aluno
          </h2>

          <p className="text-zinc-500 mt-2">
            Preencha os dados do aluno.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-5">

          <div>
            <label className="block text-sm font-semibold text-zinc-300 mb-2">
              Nome completo:
            </label>

            <input
              name="nome"
              value={aluno.nome}
              required
              onChange={(e) => handlerChange("nome", e.target.value)}
              placeholder="Digite o nome completo"
              className="w-full bg-zinc-900 border border-zinc-800 rounded-lg px-4 py-3 text-white outline-none focus:border-yellow-400 transition"
            />
          </div>

          <div>
            <label className="block text-sm font-semibold text-zinc-300 mb-2">
              CPF:
            </label>

            <input
              name="cpf"
              value={aluno.cpf}
              required
              onChange={(e) => handlerChange("cpf", e.target.value)}
              placeholder="000.000.000-00"
              className="w-full bg-zinc-900 border border-zinc-800 rounded-lg px-4 py-3 text-white outline-none focus:border-yellow-400 transition"
            />
          </div>

          <div>
            <label className="block text-sm font-semibold text-zinc-300 mb-2">
              Data de nascimento:
            </label>

            <input
              name="dataNascimento"
              type="date"
              value={aluno.dataNascimento}
              required
              onChange={(e) =>
                handlerChange("dataNascimento", e.target.value)
              }
              className="w-full bg-zinc-900 border border-zinc-800 rounded-lg px-4 py-3 text-white outline-none focus:border-yellow-400 transition"
            />
          </div>

          <div>
            <label className="block text-sm font-semibold text-zinc-300 mb-2">
              Telefone:
            </label>

            <input
              name="telefone"
              value={aluno.telefone}
              required
              onChange={(e) => handlerChange("telefone", e.target.value)}
              placeholder="(48) 99999-9999"
              className="w-full bg-zinc-900 border border-zinc-800 rounded-lg px-4 py-3 text-white outline-none focus:border-yellow-400 transition"
            />
          </div>

          <div className="md:col-span-2">
            <label className="block text-sm font-semibold text-zinc-300 mb-2">
              E-mail:
            </label>

            <input
              name="email"
              type="email"
              value={aluno.email}
              required
              onChange={(e) => handlerChange("email", e.target.value)}
              placeholder="aluno@email.com"
              className="w-full bg-zinc-900 border border-zinc-800 rounded-lg px-4 py-3 text-white outline-none focus:border-yellow-400 transition"
            />
          </div>

          <div className="md:col-span-2">
            <label className="block text-sm font-semibold text-zinc-300 mb-2">
              CEP:
            </label>

            <div className="flex gap-3">
              <input
                name="cep"
                value={aluno.cep}
                required
                onChange={(e) => handlerChange("cep", e.target.value)}
                placeholder="88888-888"
                className="flex-1 bg-zinc-900 border border-zinc-800 rounded-lg px-4 py-3 text-white outline-none focus:border-yellow-400 transition"
              />

              <button
                type="button"
                onClick={handlerBuscarCep}
                className="px-5 py-3 rounded-lg border border-yellow-400 text-yellow-400 font-bold hover:bg-yellow-400 hover:text-black transition"
              >
                Buscar CEP
              </button>
            </div>

            <p className="text-xs text-zinc-500 mt-2">
              O endereço é consultado no ViaCEP através do backend.
            </p>
          </div>

          <div className="md:col-span-2">
            <label className="block text-sm font-semibold text-zinc-300 mb-2">
              Logradouro:
            </label>

            <input
              name="logradouro"
              value={aluno.logradouro}
              readOnly={enderecoBuscado}
              onChange={(e) => handlerChange("logradouro", e.target.value)}
              className="w-full bg-zinc-900 border border-zinc-800 rounded-lg px-4 py-3 text-white outline-none focus:border-yellow-400 transition"
            />
          </div>

          <div>
            <label className="block text-sm font-semibold text-zinc-300 mb-2">
              Bairro:
            </label>

            <input
              name="bairro"
              value={aluno.bairro}
              readOnly={enderecoBuscado}
              onChange={(e) => handlerChange("bairro", e.target.value)}
              className="w-full bg-zinc-900 border border-zinc-800 rounded-lg px-4 py-3 text-white outline-none focus:border-yellow-400 transition"
            />
          </div>

          <div>
            <label className="block text-sm font-semibold text-zinc-300 mb-2">
              Cidade:
            </label>

            <input
              name="cidade"
              value={aluno.cidade}
              readOnly
              className="w-full bg-zinc-800 border border-zinc-700 rounded-lg px-4 py-3 text-zinc-500 outline-none cursor-not-allowed"
            />
          </div>

          <div>
            <label className="block text-sm font-semibold text-zinc-300 mb-2">
              UF:
            </label>

            <input
              name="uf"
              value={aluno.uf}
              readOnly
              maxLength={2}
              className="w-full bg-zinc-800 border border-zinc-700 rounded-lg px-4 py-3 text-zinc-500 outline-none cursor-not-allowed"
            />
          </div>

          <div>
            <label className="block text-sm font-semibold text-zinc-300 mb-2">
              Número:
            </label>

            <input
              name="numero"
              value={aluno.numero}
              inputMode="numeric"
              pattern="[0-9]*"
              onFocus={() => {
                if (aluno.numero === "S/N") {
                  handlerChange("numero", "");
                }
              }}
              onBlur={() => {
                if (!aluno.numero.trim()) {
                  handlerChange("numero", "S/N");
                }
              }}
              onChange={(e) => handlerChange("numero", e.target.value)}
              placeholder="S/N"
              className="w-full bg-zinc-900 border border-zinc-800 rounded-lg px-4 py-3 text-white outline-none focus:border-yellow-400 transition"
            />
          </div>

          <div>
            <label className="block text-sm font-semibold text-zinc-300 mb-2">
              Complemento:
            </label>

            <input
              name="complemento"
              value={aluno.complemento}
              onChange={(e) => handlerChange("complemento", e.target.value)}
              placeholder="Apartamento, bloco, sala..."
              className="w-full bg-zinc-900 border border-zinc-800 rounded-lg px-4 py-3 text-white outline-none focus:border-yellow-400 transition"
            />
          </div>

        </div>

        <div className="flex justify-end gap-3 mt-8">
          <Link
            href="/aluno"
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

'use client';

import { useRouter } from "next/navigation";
import { useDispatch } from "react-redux";
import { loginBack } from "../services/authService";
import { buscarUsuarioLogado } from "../services/usuarioService";
import { login, logout, setToken } from "../redux/slices/authSlice";

export default function Login(){
  const router = useRouter();
  const dispatch = useDispatch();

  const handleLogin = async(formData:FormData) =>{
      try {
          const emailTela = formData.get("email")?.toString() ?? "";
          const senhaTela = formData.get("senha")?.toString() ?? "";

          var loginResposta = await loginBack(emailTela,senhaTela);

          if(loginResposta != null){
              dispatch(setToken(loginResposta.token));

              var usuarioLogado = await buscarUsuarioLogado();

              dispatch(login(
              {
                  token:loginResposta.token,
                  usuario:{...usuarioLogado}
              }
              ));

              router.push("/home")
          }else{
              alert("Login ou senha Invalido!")
          }
      } catch (error) {
          dispatch(logout());
          alert("Login ou senha Invalido!")
      }
  }

  return (
    <main className="min-h-screen bg-[#070707] text-white">
      <div className="grid min-h-screen lg:grid-cols-[1.05fr_1fr]">
        <section className="relative hidden overflow-hidden border-r border-zinc-900 lg:flex lg:items-center">
          <div className="absolute -left-36 -top-36 h-96 w-96 rounded-full border border-[#F5C518]/30" />

          <div className="absolute -left-16 -top-16 h-96 w-96 rounded-full border border-[#F5C518]/10" />

          <div className="absolute -bottom-48 right-10 h-[520px] w-[520px] rounded-full border border-[#F5C518]/20" />

          <div className="absolute inset-0 bg-[radial-gradient(circle_at_25%_35%,rgba(245,197,24,0.08),transparent_30%)]" />

          <div className="relative z-10 max-w-xl px-16">
            <h1 className="text-6xl font-black tracking-tight">
              Tigre<span className="text-[#F5C518]">Gym</span>
            </h1>

            <p className="mt-6 max-w-md text-2xl leading-relaxed text-zinc-300">
              Gestão completa para sua academia
            </p>

            <div className="mt-6 h-1 w-12 rounded-full bg-[#F5C518]" />
          </div>
        </section>

        <section className="flex items-center justify-center px-6 py-12">
          <div className="w-full max-w-md rounded-2xl border border-zinc-800 bg-[#0d1215] p-8 shadow-2xl shadow-black/40">
            <div className="mb-8">
              <h2 className="text-3xl font-black">Bem-vindo!</h2>

              <p className="mt-2 text-sm text-zinc-400">
                Acesse sua conta para continuar
              </p>
            </div>

            <form action={handleLogin} className="space-y-5">
              <div>
                <label className="mb-2 block text-sm font-semibold text-zinc-300">
                  E-mail
                </label>

                <input
                  name="email"
                  type="email"
                  required
                  placeholder="Digite seu e-mail"
                  className="w-full rounded-lg border border-zinc-700 bg-[#11181c] px-4 py-3 text-white outline-none transition placeholder:text-zinc-600 focus:border-[#F5C518] focus:ring-2 focus:ring-[#F5C518]/10"
                />
              </div>

              <div>
                <label className="mb-2 block text-sm font-semibold text-zinc-300">
                  Senha
                </label>

                <input
                  name="senha"
                  type="password"
                  required
                  placeholder="Digite sua senha"
                  className="w-full rounded-lg border border-zinc-700 bg-[#11181c] px-4 py-3 text-white outline-none transition placeholder:text-zinc-600 focus:border-[#F5C518] focus:ring-2 focus:ring-[#F5C518]/10"
                />
              </div>

              <button
                type="submit"
                className="w-full rounded-lg bg-[#F5C518] py-3 font-bold text-black transition hover:bg-[#ffd52c]"
              >
                Entrar
              </button>
            </form>
          </div>
        </section>
      </div>
    </main>
  );
}

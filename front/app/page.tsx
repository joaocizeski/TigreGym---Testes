"use client";

import Image from "next/image";
import Link from "next/link";

export default function Home() {
  return (
    <>
      <header className="header">
        <div className="container navbar">
          <a href="#inicio" className="marca">
            <Image
              src="/logo-tigregym-v2.png"
              alt="Logo TigreGym"
              width={165}
              height={70}
              className="logo"
              priority
            />
          </a>

          <nav className="menu">
            <a href="#inicio">Início</a>
            <a href="#beneficios">Benefícios</a>
            <a href="#historia">Nossa história</a>
            <Link href="/login" className="login">
              Login
            </Link>
          </nav>
        </div>
      </header>

      <main>
        {/* HERO */}
        <section className="hero" id="inicio">
          <div className="hero-overlay" />

          <div className="container hero-conteudo">
            <div className="hero-texto">
              <h1>
                SUA ACADEMIA.
                <br />
                MAIS <strong>SIMPLES.</strong>
              </h1>

              <p>
                O TigreGym é uma plataforma moderna e acessível para facilitar a
                gestão da academia e melhorar a experiência dos alunos.
              </p>

              <div className="hero-botoes">
                <Link href="/login" className="botao-principal">
                  Acessar TigreGym
                  <span>→</span>
                </Link>

                <a href="#historia" className="botao-secundario">
                  Conheça nossa história
                </a>
              </div>

              <div className="hero-detalhes">
                <div>
                  <b>▥</b>

                  <span>
                    Gestão
                    <small>mais organizada</small>
                  </span>
                </div>

                <div>
                  <b>●</b>

                  <span>
                    Experiência
                    <small>simples para o aluno</small>
                  </span>
                </div>

                <div>
                  <b>↗</b>

                  <span>
                    Evolução
                    <small>para sua academia</small>
                  </span>
                </div>
              </div>
            </div>

            <div className="hero-logo-area">
              <div className="brilho-logo" />

              <Image
                src="/logo-tigregym-v2.png"
                alt="TigreGym"
                width={580}
                height={300}
                className="logo-grande"
                priority
              />
            </div>
          </div>
        </section>

        {/* BENEFÍCIOS */}
        <section className="beneficios" id="beneficios">
          <div className="container">
            <div className="titulo-section">
              <div className="mini-titulo">POR QUE TIGREGYM?</div>

              <h2>
                Academia sem
                <span> complicação.</span>
              </h2>

              <p>
                Tudo o que a academia precisa de forma simples, organizada e
                fácil de utilizar.
              </p>
            </div>

            <div className="cards">
              <article className="card">
                <div className="icone-card">01</div>

                <h3>Gestão completa</h3>

                <p>
                  Organize alunos, planos e matrículas em um único sistema,
                  mantendo as informações importantes sempre acessíveis.
                </p>
              </article>

              <article className="card">
                <div className="icone-card">02</div>

                <h3>Experiência do aluno</h3>

                <p>
                  Uma plataforma moderna e intuitiva, desenvolvida para ser
                  utilizada com facilidade por pessoas de diferentes idades.
                </p>
              </article>

              <article className="card">
                <div className="icone-card">03</div>

                <h3>Mais organização</h3>

                <p>
                  Uma rotina mais simples e eficiente para a academia, reduzindo
                  complicações no dia a dia.
                </p>
              </article>
            </div>
          </div>
        </section>

        {/* HISTÓRIA */}
        <section className="historia" id="historia">
          <div className="container historia-grid">
            <div className="historia-visual">
              <div className="historia-linha" />

              <Image
                src="/logo-tigregym-v2.png"
                alt="TigreGym"
                width={550}
                height={290}
                className="historia-logo"
              />
            </div>

            <div className="historia-texto">
              <div className="mini-titulo">NOSSA HISTÓRIA</div>

              <h2>
                FEITO EM CRICIÚMA
                <br />
                PARA GRANDES <span>RESULTADOS.</span>
              </h2>

              <p>
                O TigreGym nasceu da ideia de um torcedor do Criciúma que
                percebeu uma oportunidade de melhorar a experiência das pessoas
                com aplicativos de academia.
              </p>

              <p>
                Ao observar as opções disponíveis atualmente, percebeu que
                muitos sistemas eram complicados de usar, possuíam funções
                desnecessárias ou tinham um custo elevado.
              </p>

              <p>
                A partir disso surgiu uma solução voltada principalmente para
                <strong> Criciúma e região</strong>, com uma proposta simples:
                ser moderna, minimalista e fácil de utilizar.
              </p>

              <p>
                O nome <strong>TigreGym</strong> representa a ligação com a
                região e faz referência ao Criciúma Esporte Clube, conhecido
                como Tigre.
              </p>
            </div>
          </div>
        </section>

        {/* IDENTIDADE */}
        <section className="identidade">
          <div className="container identidade-grid">
            <div className="identidade-texto">
              <div className="mini-titulo">NOSSA IDENTIDADE</div>

              <h2>
                CRICIÚMA
                <br />
                EM CADA <span>DETALHE.</span>
              </h2>

              <p>
                O preto e o branco predominam na identidade do TigreGym,
                enquanto o amarelo aparece nos elementos de destaque, trazendo
                uma ligação visual com o Criciúma e com a região.
              </p>

              <div className="cores">
                <div className="cor cor-preta">
                  <span>PRETO</span>
                </div>

                <div className="cor cor-branca">
                  <span>BRANCO</span>
                </div>

                <div className="cor cor-amarela">
                  <span>AMARELO</span>
                </div>
              </div>
            </div>

            <div className="identidade-logo">
              <Image
                src="/logo-tigregym-v2.png"
                alt="Identidade TigreGym"
                width={550}
                height={290}
              />
            </div>
          </div>
        </section>

        {/* CTA */}
        <section className="cta">
          <div className="container cta-box">
            <div>
              <div className="mini-titulo">TIGREGYM</div>

              <h2>
                SIMPLES PARA A ACADEMIA.
                <br />
                FÁCIL PARA O <span>ALUNO.</span>
              </h2>

              <p>
                Tecnologia, organização e uma experiência moderna em um sistema
                pensado para ser simples.
              </p>
            </div>

            <Link href="/login" className="botao-principal">
              Acessar agora
              <span>→</span>
            </Link>
          </div>
        </section>
      </main>

      {/* FOOTER */}
      <footer className="footer">
        <div className="container footer-conteudo">
          <Image
            src="/logo-tigregym-v2.png"
            alt="TigreGym"
            width={145}
            height={60}
            className="footer-logo"
          />

          <div className="footer-links">
            <a href="#inicio">Início</a>

            <a href="#beneficios">Benefícios</a>

            <a href="#historia">Nossa história</a>
          </div>

          <div className="footer-final">
            <span>Criciúma - Santa Catarina</span>

            <small>© 2026 TigreGym. Todos os direitos reservados.</small>
          </div>
        </div>
      </footer>

      <style jsx>{`
        :global(*) {
          margin: 0;
          padding: 0;
          box-sizing: border-box;
        }

        :global(html) {
          scroll-behavior: smooth;
          background: #070707;
        }

        :global(body) {
          margin: 0;
          background: #070707;
          color: #ffffff;
          font-family: Arial, Helvetica, sans-serif;
        }

        :global(a) {
          text-decoration: none;
        }

        .container {
          width: min(1180px, 90%);
          margin: 0 auto;
        }

        /* HEADER */

        .header {
          position: fixed;
          top: 0;
          left: 0;
          width: 100%;
          z-index: 1000;

          background: rgba(5, 5, 5, 0.88);
          backdrop-filter: blur(15px);

          border-bottom: 1px solid rgba(255, 255, 255, 0.07);
        }

        .navbar {
          height: 82px;

          display: flex;
          align-items: center;
          justify-content: space-between;
        }

        .marca {
          display: flex;
          align-items: center;
        }

        .logo {
          width: 165px;
          height: 65px;
          object-fit: contain;
        }

        .menu {
          display: flex;
          align-items: center;
          gap: 34px;
        }

        .menu a {
          color: #b5b5b5;
          font-size: 0.9rem;
          transition: 0.2s;
        }

        .menu a:hover {
          color: white;
        }

        .menu .login {
          padding: 11px 25px;
          border-radius: 8px;

          color: #080808;
          background: #f5c518;

          font-weight: 800;
        }

        .menu .login:hover {
          color: #080808;
          background: #ffd530;
        }

        /* HERO */

        .hero {
          position: relative;

          min-height: 100vh;

          display: flex;
          align-items: center;

          padding: 130px 0 70px;

          overflow: hidden;

          background:
            radial-gradient(
              circle at 80% 35%,
              rgba(245, 197, 24, 0.13),
              transparent 27%
            ),
            linear-gradient(115deg, #070707 0%, #0a0a0a 65%, #11100b 100%);
        }

        .hero-overlay {
          position: absolute;
          inset: 0;

          background: linear-gradient(
            90deg,
            rgba(0, 0, 0, 0.25),
            transparent 55%
          );

          pointer-events: none;
        }

        .hero-conteudo {
          position: relative;
          z-index: 2;

          display: grid;

          grid-template-columns: 1fr 0.85fr;

          align-items: center;

          gap: 75px;
        }

        .hero-texto {
          max-width: 670px;
        }

        .hero-mini {
          display: flex;
          align-items: center;

          gap: 11px;

          margin-bottom: 22px;

          color: #f5c518;

          font-size: 0.72rem;
          font-weight: 800;

          letter-spacing: 3px;
        }

        .hero-mini span {
          width: 28px;
          height: 3px;

          background: #f5c518;
        }

        .hero h1 {
          margin-bottom: 27px;

          color: white;

          font-size: clamp(4rem, 7vw, 6.6rem);

          line-height: 0.91;

          letter-spacing: -5px;

          font-weight: 950;
        }

        .hero h1 strong {
          color: #f5c518;
        }

        .hero-texto > p {
          max-width: 590px;

          margin-bottom: 34px;

          color: #adadad;

          font-size: 1.03rem;

          line-height: 1.75;
        }

        .hero-botoes {
          display: flex;
          align-items: center;

          gap: 15px;
        }

        .botao-principal {
          display: inline-flex;
          align-items: center;
          justify-content: center;

          gap: 13px;

          padding: 14px 25px;

          border-radius: 8px;

          background: #f5c518;
          color: #080808;

          font-size: 0.91rem;
          font-weight: 850;

          box-shadow: 0 10px 40px rgba(245, 197, 24, 0.14);

          transition: 0.25s;
        }

        .botao-principal:hover {
          transform: translateY(-2px);

          background: #ffd530;
        }

        .botao-principal span {
          font-size: 1.2rem;
        }

        .botao-secundario {
          padding: 13px 24px;

          color: #ffffff;

          border-radius: 8px;

          border: 1px solid rgba(255, 255, 255, 0.35);

          font-size: 0.9rem;
          font-weight: 700;

          transition: 0.2s;
        }

        .botao-secundario:hover {
          background: rgba(255, 255, 255, 0.06);
        }

        .hero-detalhes {
          display: flex;

          gap: 34px;

          margin-top: 42px;
        }

        .hero-detalhes > div {
          display: flex;
          align-items: center;

          gap: 10px;
        }

        .hero-detalhes b {
          color: #f5c518;

          font-size: 1.4rem;
        }

        .hero-detalhes span {
          display: flex;
          flex-direction: column;

          color: #d5d5d5;

          font-size: 0.76rem;
          font-weight: 700;
        }

        .hero-detalhes small {
          margin-top: 3px;

          color: #747474;

          font-size: 0.66rem;
          font-weight: 500;
        }

        .hero-logo-area {
          min-height: 470px;

          display: flex;
          align-items: center;
          justify-content: center;

          position: relative;
        }

        .brilho-logo {
          position: absolute;

          width: 400px;
          height: 400px;

          border-radius: 50%;

          background: rgba(245, 197, 24, 0.12);

          filter: blur(110px);
        }

        .logo-grande {
          position: relative;
          z-index: 2;

          width: 100%;
          max-width: 570px;

          height: auto;

          object-fit: contain;

          filter: drop-shadow(0 30px 50px rgba(0, 0, 0, 0.5));
        }

        /* BENEFÍCIOS */

        .beneficios {
          padding: 110px 0;

          background: #0b0b0b;

          border-top: 1px solid rgba(255, 255, 255, 0.05);
          border-bottom: 1px solid rgba(255, 255, 255, 0.05);
        }

        .titulo-section {
          max-width: 650px;

          margin-bottom: 48px;
        }

        .mini-titulo {
          margin-bottom: 12px;

          color: #f5c518;

          font-size: 0.7rem;
          font-weight: 800;

          letter-spacing: 3px;

          text-transform: uppercase;
        }

        .titulo-section h2,
        .historia h2,
        .identidade h2,
        .cta h2 {
          color: white;

          font-size: clamp(2.5rem, 5vw, 4rem);

          line-height: 1;

          letter-spacing: -2px;

          font-weight: 950;
        }

        .titulo-section h2 span,
        .historia h2 span,
        .identidade h2 span,
        .cta h2 span {
          color: #f5c518;
        }

        .titulo-section > p {
          margin-top: 19px;

          color: #878787;

          line-height: 1.7;
        }

        .cards {
          display: grid;

          grid-template-columns: repeat(3, 1fr);

          gap: 18px;
        }

        .card {
          min-height: 250px;

          padding: 31px;

          border-radius: 13px;

          background: linear-gradient(145deg, #111111, #0d0d0d);

          border: 1px solid rgba(255, 255, 255, 0.08);

          transition: 0.25s;
        }

        .card:hover {
          transform: translateY(-5px);

          border-color: rgba(245, 197, 24, 0.3);
        }

        .icone-card {
          width: 47px;
          height: 47px;

          display: flex;
          align-items: center;
          justify-content: center;

          margin-bottom: 25px;

          border-radius: 9px;

          background: #f5c518;

          color: #080808;

          font-weight: 900;
        }

        .card h3 {
          margin-bottom: 12px;

          font-size: 1.09rem;
        }

        .card p {
          color: #8e8e8e;

          font-size: 0.88rem;

          line-height: 1.7;
        }

        /* HISTÓRIA */

        .historia {
          padding: 125px 0;

          background:
            radial-gradient(
              circle at 15% 50%,
              rgba(245, 197, 24, 0.07),
              transparent 22%
            ),
            #080808;
        }

        .historia-grid {
          display: grid;

          grid-template-columns: 0.9fr 1.1fr;

          align-items: center;

          gap: 95px;
        }

        .historia-visual {
          min-height: 430px;

          display: flex;
          align-items: center;
          justify-content: center;

          position: relative;
        }

        .historia-linha {
          position: absolute;

          width: 350px;
          height: 350px;

          border-radius: 50%;

          border: 1px solid rgba(245, 197, 24, 0.12);
        }

        .historia-logo {
          position: relative;
          z-index: 2;

          width: 100%;
          max-width: 500px;

          height: auto;
        }

        .historia-texto p {
          margin-top: 17px;

          color: #969696;

          line-height: 1.7;

          font-size: 0.93rem;
        }

        .historia-texto strong {
          color: white;
        }

        /* IDENTIDADE */

        .identidade {
          padding: 120px 0;

          background: linear-gradient(120deg, #101010, #080808);

          border-top: 1px solid rgba(255, 255, 255, 0.05);

          border-bottom: 1px solid rgba(255, 255, 255, 0.05);
        }

        .identidade-grid {
          display: grid;

          grid-template-columns: 1fr 0.9fr;

          align-items: center;

          gap: 80px;
        }

        .identidade-texto p {
          max-width: 620px;

          margin-top: 22px;

          color: #969696;

          line-height: 1.7;
        }

        .cores {
          display: flex;

          gap: 10px;

          margin-top: 35px;
        }

        .cor {
          width: 100px;
          height: 75px;

          display: flex;
          align-items: flex-end;

          padding: 10px;

          border-radius: 8px;

          border: 1px solid rgba(255, 255, 255, 0.15);
        }

        .cor span {
          font-size: 0.57rem;

          font-weight: 800;

          letter-spacing: 1px;
        }

        .cor-preta {
          background: #080808;

          color: white;
        }

        .cor-branca {
          background: white;

          color: #080808;
        }

        .cor-amarela {
          background: #f5c518;

          color: #080808;
        }

        .identidade-logo {
          display: flex;
          justify-content: center;
        }

        .identidade-logo img {
          width: 100%;
          height: auto;
        }

        /* CTA */

        .cta {
          padding: 105px 0;

          background:
            radial-gradient(
              circle at 85% 50%,
              rgba(245, 197, 24, 0.11),
              transparent 26%
            ),
            #070707;
        }

        .cta-box {
          display: flex;
          align-items: center;
          justify-content: space-between;

          gap: 70px;

          padding: 50px;

          border-radius: 18px;

          border: 1px solid rgba(245, 197, 24, 0.18);

          background: rgba(255, 255, 255, 0.025);
        }

        .cta p {
          max-width: 600px;

          margin-top: 17px;

          color: #8a8a8a;

          line-height: 1.65;
        }

        /* FOOTER */

        .footer {
          padding: 30px 0;

          background: #050505;

          border-top: 1px solid rgba(255, 255, 255, 0.07);
        }

        .footer-conteudo {
          display: grid;

          grid-template-columns:
            180px
            1fr
            auto;

          align-items: center;

          gap: 50px;
        }

        .footer-logo {
          width: 140px;
          height: auto;
        }

        .footer-links {
          display: flex;

          justify-content: center;

          gap: 27px;
        }

        .footer-links a {
          color: #777;

          font-size: 0.75rem;
        }

        .footer-links a:hover {
          color: white;
        }

        .footer-final {
          display: flex;
          flex-direction: column;

          align-items: flex-end;

          gap: 6px;

          color: #858585;

          font-size: 0.72rem;
        }

        .footer-final small {
          color: #555;

          font-size: 0.66rem;
        }

        /* RESPONSIVO */

        @media (max-width: 900px) {
          .hero-conteudo,
          .historia-grid,
          .identidade-grid {
            grid-template-columns: 1fr;
          }

          .hero-logo-area {
            min-height: 370px;
          }

          .cards {
            grid-template-columns: 1fr;
          }

          .historia-grid,
          .identidade-grid {
            gap: 45px;
          }

          .cta-box {
            flex-direction: column;

            align-items: flex-start;
          }

          .footer-conteudo {
            grid-template-columns: 1fr;

            gap: 25px;
          }

          .footer-links {
            justify-content: flex-start;
          }

          .footer-final {
            align-items: flex-start;
          }
        }

        @media (max-width: 600px) {
          .navbar {
            height: 72px;
          }

          .logo {
            width: 130px;
          }

          .menu a:not(.login) {
            display: none;
          }

          .menu .login {
            padding: 9px 17px;
          }

          .hero {
            padding-top: 115px;
          }

          .hero h1 {
            font-size: 3.5rem;

            letter-spacing: -3px;
          }

          .hero-botoes {
            flex-direction: column;

            align-items: stretch;
          }

          .botao-principal,
          .botao-secundario {
            text-align: center;
          }

          .hero-detalhes {
            gap: 16px;

            flex-wrap: wrap;
          }

          .hero-logo-area {
            min-height: 280px;
          }

          .titulo-section h2,
          .historia h2,
          .identidade h2,
          .cta h2 {
            font-size: 2.5rem;
          }

          .historia-visual {
            min-height: 300px;
          }

          .historia-linha {
            width: 270px;
            height: 270px;
          }

          .cta-box {
            padding: 35px 25px;
          }

          .footer-links {
            flex-wrap: wrap;
          }
        }
      `}</style>
    </>
  );
}

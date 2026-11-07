export class Aluno {
  constructor(
    public id: number | null,
    public nome: string,
    public cpf: string,
    public dataNascimento: string,
    public telefone: string,
    public email: string,
    public cep: string,
    public logradouro: string,
    public numero: string,
    public complemento: string,
    public bairro: string,
    public cidade: string,
    public uf: string,
    public status: string
  ) {}
}

export interface AlunoFormProps {
  alunoExistente?: Aluno;
}

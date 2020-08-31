package arbolgrafico;

import java.util.*;
import javax.swing.JPanel;

public class ArbodeExpresiones {
	Stack<Nodo> pOperandos = new Stack<Nodo>();
	Stack<String> pOperadores = new Stack<String>();

	private Nodo raiz;

	final String blanco;
	final String operadores;

	public ArbodeExpresiones() {
		blanco = " \t";
		operadores = ")+-*/%^(";
	}

	public Nodo getRaiz() {
		return this.raiz;
	}

	public void setRaiz(Nodo r) {
		this.raiz = r;
	}

	public boolean contruir(String con) {
		construirArbol(con);
		return true;
	}

	public Nodo construirArbol(String expresion) {
		StringTokenizer tokenizer;
		String token;

		tokenizer = new StringTokenizer(expresion, blanco + operadores, true);
		while (tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();
			if (blanco.indexOf(token) >= 0) {
			} else if (operadores.indexOf(token) < 0) {
				pOperandos.push(new Nodo(token));
			} else if (token.equals(")")) {
				while (!pOperadores.empty() && !pOperadores.peek().equals("(")) {
					guardarSubArbol();
				}
				pOperadores.pop();
			} else {
				if (!token.equals("(") && !pOperadores.empty()) {
					// operador diferente de cualquier parentesis
					String op = (String) pOperadores.peek();
					while (!op.equals("(") && !pOperadores.empty()
							&& operadores.indexOf(op) >= operadores.indexOf(token)) {
						guardarSubArbol();
						if (!pOperadores.empty())
							op = (String) pOperadores.peek();
					}
				}
				pOperadores.push(token);
			}
		}

		raiz = (Nodo) pOperandos.peek();
		while (!pOperadores.empty()) {
			if (pOperadores.peek().equals("(")) {
				pOperadores.pop();
			} else {
				guardarSubArbol();
				raiz = (Nodo) pOperandos.peek();
			}
		}
		return raiz;
	}

	private void guardarSubArbol() {
		Nodo op2 = (Nodo) pOperandos.pop();
		Nodo op1 = (Nodo) pOperandos.pop();
		pOperandos.push(new Nodo(op2, pOperadores.pop(), op1));

	}

	public void imprime(Nodo n) {
		if (n != null) {

			imprime(n.getNodoDerecho());
			System.out.print(n.getInformacion() + " ");
			imprime(n.getNodoIzquierdo());
		}
	}

	public void imprimePos(Nodo n) {
		if (n != null) {
			imprimePos(n.getNodoIzquierdo());
			imprimePos(n.getNodoDerecho());
			System.out.print(n.getInformacion() + " ");
		}
	}

	public void imprimePre(Nodo n) {
		if (n != null) {
			System.out.print(n.getInformacion() + " ");

			imprimePre(n.getNodoDerecho());
			imprimePre(n.getNodoIzquierdo());
		}
	}

	public JPanel getdibujo() {
		return new ArbolExpresionGrafico(this);
	}
}

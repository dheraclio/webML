Metamodelo
    Atributos do tipo WebML Type foram transformados em associações 1:1
    Atributos chamado duration foram ignorados por não terem tipo definido

    Associações sem papeis foram chamadas de OWNER e ELEMENT
    Associações ciclicas foram ignoradas devido a conflito de nomes no modelmanager
	Atributo id não foi incluído pois não descobri como adicionar novo tipo declarado pelo profile webml	
	Metaclasses originais foram prefixadas com 'WebML_' para evitar conflito com as outros metamodelos
	Metamodelo de WebML foi escrito sobre metaclasses e relacionamentos de UML, prefixados com 'WEBMLUML_'
	
DataView Package
    Herança entre DataModelElement e Domain está invertida
	

HypertextView Package
    Dependencias invertidas:    AreaView -> UnitView
                                TransactionView -> UnitView

	UnitView
		Adicionada classe GetUnit para permitir execução da invariante OCL fornecida
	
Modificações no framework
 
	IXMIParser(1.2-SNAPSHOT) e XMIParser(1.2-SNAPSHOT)
		Adicionado metodo 'public boolean getStereoTypeName(String stereoTypeId)'
	TCLib(1.2-SNAPSHOT)
		Domain
			Criadas booleanas metamodelCreated e specificationOfCurrentDiagramCreated para respectivos controles
			Adicionados setters para variáveis booleanas da classe.
			Metodo parse não lança exceção se já foi feito parsing antes, apenas não faz nada.
			Mesmo para metodo validate().
		
		JoindeDomain
			Metodo transform(), foi adicionado setParsed(true) no dominio alvo pois equivale ao resultado da transformação.		
			Booleana estatica CHAINED adicionada para permitir execução em sequencia. Será substituida por classe ChainedJoinedDomain
			createClassDiagram() closeClassDiagram() externalisados para execução em sequencia (ChainedJoinedDomain).
			
		ModelManager 
			Modificada para createClassDiagram() só criar se não existir um ClassDiagram
			Modificada para createObjectDiagram() só criar se não existir um ObjectDiagram
			
		TransformationContract
			Controle de criação e fechamento do objectDiagram externalisado.
			
		
		ChainTransformationContract
			Classe para execução de tranformações em sequencia
			
	WebML Transformer
		Transformação
			Criadas class Modeler e Parser para os domínios
			Metamodelo de WebML completamente criado, extendendo metamodelo UML.
			Refatoração da class UMLDomain
			Invariants carregadas a partir de arquivo propertie em todos os domínios
			Transformação estruturada através de aplicação de regras
		Aplicação
			A partir da aplicação XMIGui foi criada uma versão inicial para o tranformador WebML
			
		Teste Unitários
	
Resultados
	A transformação em memória entre WebML e UML ocorre, mas de WebML para EJB apresenta erros.
	
Sugestões
	Detectar tentativas de sobre-escrita de metaclasses
	
Planos futuros
	Concluir transformação básica
	Adicionar interfaces ao modelo de entrada
	Gerar interfaces através de richface e semelhantes.	
	

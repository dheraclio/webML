Metamodelo
    Atributos do tipo WebML Type foram transformados em associações 1:1
    Atributos chamado duration foram ignorados por não terem tipo definido

    Associações sem papeis foram chamadas de OWNER e ELEMENT
    Associações ciclicas foram ignoradas devido a conflito de nomes no modelmanager
	Atributo id não foi incluído pois não descobri como adicionar novo tipo declarado pelo profile webml
DataView Package
    Herança entre DataModelElement e Domain está invertida
	

HypertextView Package
    Dependencias invertidas:    AreaView -> UnitView
                                TransactionView -> UnitView

	UnitView
		Adicionada classe GetUnit para permitir execução da invariante OCL fornecida
		
	
Problemas	
Planos futuros
	Gerar interfaces através de richface e semelhantes.
	
	
Modificações no framework
	Adicionado public boolean getStereoTypeName(String stereoTypeId); em IXMIParser(1.2-SNAPSHOT) e XMIParser(1.2-SNAPSHOT);
	
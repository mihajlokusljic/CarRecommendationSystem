package kusljic.mihajlo.sbnz.spring.backend.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.drools.template.ObjectDataCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kusljic.mihajlo.sbnz.spring.backend.facts.TrendingConstraint;
import kusljic.mihajlo.sbnz.spring.backend.repository.TrendingConstraintRepository;
import kusljic.mihajlo.sbnz.spring.backend.service.KnowledgeEngineService;
import kusljic.mihajlo.sbnz.spring.backend.service.TrendingConstraintService;

@Service
public class TrendingConstraintServiceImpl implements TrendingConstraintService {

	private static final String TRENDING_RULES_TEMPLATE_PATH = "..\\drools-spring-kjar\\src\\main\\resources\\sbnz\\templates\\trending_rules.drt";
	private static final String TRENDING_RULES_FILE_PATH = "..\\drools-spring-kjar\\src\\main\\resources\\sbnz\\integracija\\trending_rules.drl";
	private KnowledgeEngineService knowledgeEngineService;
	private TrendingConstraintRepository trendingConstraintRepository;
	
	
	@Autowired
	public TrendingConstraintServiceImpl(TrendingConstraintRepository trendingConstraintRepository, KnowledgeEngineService knowledgeEngineService) {
		super();
		this.trendingConstraintRepository = trendingConstraintRepository;
		this.knowledgeEngineService = knowledgeEngineService;
	}

	@Override
	public TrendingConstraint setTrendingConstraint(TrendingConstraint newConstraint) {
		TrendingConstraint currentConstraint = this.getTrendingConstraint();
		if (currentConstraint == null) {
			currentConstraint = newConstraint;
		} else {
			currentConstraint.setMinimumRecommendations(newConstraint.getMinimumRecommendations());
			currentConstraint.setTimeWindow(newConstraint.getTimeWindow());
		}
		
		this.updateTrendingRules(newConstraint);
		this.knowledgeEngineService.updateTrending();
		return this.trendingConstraintRepository.save(currentConstraint);
	}

	@Override
	public TrendingConstraint getTrendingConstraint() {
		List<TrendingConstraint> constraintList = this.trendingConstraintRepository.findAll();
		if (constraintList.isEmpty()) {
			return null;
		}
		return constraintList.get(0);
	}
	
	private void updateTrendingRules(TrendingConstraint newConstraint) {
		try {
			// Get the template
			InputStream template = new FileInputStream(TRENDING_RULES_TEMPLATE_PATH);
			
			// Compile template to generate new rules
			List<TrendingConstraint> arguments = new ArrayList<TrendingConstraint>();
			arguments.add(newConstraint);
			ObjectDataCompiler compiler = new ObjectDataCompiler();
			String drl = compiler.compile(arguments, template);
			
			// Save rule to drl file
			FileOutputStream drlFile = new FileOutputStream(new File(TRENDING_RULES_FILE_PATH));
			drlFile.write(drl.getBytes());
			drlFile.close();
			
			// Update Rules project
			InvocationRequest request = new DefaultInvocationRequest();
	        request.setPomFile( new File( "../drools-spring-kjar/pom.xml" ) );
	        request.setGoals( Arrays.asList( "clean", "install" ) );

	        Invoker invoker = new DefaultInvoker();
	        invoker.execute( request );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MavenInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

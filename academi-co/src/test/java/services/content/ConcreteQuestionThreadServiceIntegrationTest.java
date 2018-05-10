//package services.content;
//
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.JavaArchive;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import dom.content.PostFactory;
//import dom.tags.TagFactory;
//
//@RunWith(Arquillian.class)
//public class ConcreteQuestionThreadServiceIntegrationTest {
//	
//	@Deployment
//	static public JavaArchive createDeployment() {
//		return ShrinkWrap.create(JavaArchive.class)
//				.addPackage(ConcreteQuestionThreadServiceIntegrationTest.class.getPackage())
//				.addPackage(PostFactory.class.getPackage())
//				.addPackage(TagFactory.class.getPackage())
//				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//	}
//
//	@Test
//	public void test() {
//		System.out.println("hihi");
//	}
//
//}

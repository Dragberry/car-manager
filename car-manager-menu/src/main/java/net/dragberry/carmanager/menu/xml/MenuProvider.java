package net.dragberry.carmanager.menu.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class MenuProvider {
	
	private static final Logger LOG = LogManager.getLogger(MenuProvider.class.getName());

	public static MainMenuXML readMainMenu() {
		JAXBContext jc;
		MainMenuXML mainMenu = null;
		try {
			jc = JAXBContext.newInstance(MainMenuXML.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	        mainMenu = (MainMenuXML) unmarshaller.unmarshal(classLoader.getResourceAsStream("MainMenu.xml"));
		} catch (JAXBException e) {
			LOG.error("Exception while reading MainMenu.xml", e);
		}
		return mainMenu;

	}

}

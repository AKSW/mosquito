/**
 * 
 */
package org.aksw.iguana.rp.storage.impl;

import org.aksw.iguana.commons.annotation.Shorthand;
import org.aksw.iguana.rp.config.CONSTANTS;
import org.aksw.iguana.rp.storage.TripleBasedStorage;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Properties;

/**
 * 
 * Will save results as NTriple File
 * 
 * @author f.conrads
 *
 */
@Shorthand("NTFileStorage")
public class NTFileStorage extends TripleBasedStorage {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(NTFileStorage.class);
	
	private StringBuilder file;
	
	/**
	 * 
	 */
	public NTFileStorage() {
		Calendar now = Calendar.getInstance();
		
		this.file = new StringBuilder();
		file.append("results_").append(now.get(Calendar.DAY_OF_MONTH)).append("-")
			.append(now.get(Calendar.MONTH)).append("-")
			.append(now.get(Calendar.YEAR)).append("_")
			.append(now.get(Calendar.HOUR_OF_DAY)).append("-")
			.append(now.get(Calendar.MINUTE)).append(".nt");
	}

	public NTFileStorage(String fileName){
		this.file = new StringBuilder(fileName);
	}
	
	/* (non-Javadoc)
	 * @see org.aksw.iguana.rp.storage.Storage#commit()
	 */
	@Override
	public void commit() {
        try (OutputStream os = new FileOutputStream(file.toString())) {
			RDFDataMgr.write(os, metricResults, RDFFormat.NTRIPLES);
		} catch (IOException e) {
			LOGGER.error("Could not commit to NTFileStorage.", e);
		}
	}


	
	@Override
	public String toString(){
		return this.getClass().getSimpleName();
	}

}

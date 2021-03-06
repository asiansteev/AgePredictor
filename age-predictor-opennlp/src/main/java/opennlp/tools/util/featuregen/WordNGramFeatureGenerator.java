/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package opennlp.tools.util.featuregen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import opennlp.tools.util.InvalidFormatException;

/**
 * Generates ngram features for a document.
 * n-gram {@link FeatureGenerator}
 */
public class WordNGramFeatureGenerator implements FeatureGenerator {

    public static final WordNGramFeatureGenerator INSTANCE = new WordNGramFeatureGenerator();
    
    //default values for bigrams
    private int minGram = 2;
    private int maxGram = 2;

    /**
     * Constructor for ngrams.
     *
     * @param minGram minGram value - which means minimum words in ngram features
     * @param maxGram maxGram value - which means maximum words in ngram features
     * @throws InvalidFormatException
     */
    public WordNGramFeatureGenerator(int minGram, int maxGram) throws InvalidFormatException {
	if (minGram > 0 && maxGram > 0) {
	    if (minGram <= maxGram) {
		this.minGram = minGram;
		this.maxGram = maxGram;
	    } else {
		throw new InvalidFormatException("Minimum range value (minGram) should be less than or equal to maximum range value (maxGram)!");
	    }
	} else {
	    throw new InvalidFormatException("Both minimum range value (minGram) & maximum range value (maxGram) should be greater than or equal to 1!");
	}
    }

    /**
     * Default constructor for Bi grams
     */
    public WordNGramFeatureGenerator() {
    }

    /**
     * Extract ngram features from given text fragments
     *
     * @param text      the text fragments to extract features from
     * @param extraInfo optional extra information
     * @return a collection of n gram features
     */
    @Override
    public Collection<String> extractFeatures(String[] text) {
	List<String> features = new ArrayList<String>();

	for (int i = 0; i <= text.length - minGram; i++) {
	    String feature = "ng=" + text[i];
	    for (int y = 1; y < maxGram && i + y < text.length; y++) {
		feature = feature + ":" + text[i + y];
		int gramCount = y + 1;
		if (maxGram >= gramCount && gramCount >= minGram) {
		    features.add(feature);
		}
	    }
	}

	return features;
    }
}
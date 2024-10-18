package enginetest.EngineFunctions;

import com.jme3.app.SimpleApplication;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.ToneMapFilter;

    public class PostProcessing {
        private SimpleApplication app;

        public PostProcessing(SimpleApplication app) {
            this.app = app;
        }

        public void addBloom(float intensity) {
            FilterPostProcessor fpp = new FilterPostProcessor(app.getAssetManager());
            BloomFilter bloom = new BloomFilter();
            ToneMapFilter toneMap = new ToneMapFilter();
            fpp.addFilter(toneMap);
            bloom.setBloomIntensity(intensity);
            fpp.addFilter(bloom);
            app.getViewPort().addProcessor(fpp);
        }
        
        
}

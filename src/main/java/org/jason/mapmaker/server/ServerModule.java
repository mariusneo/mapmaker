package org.jason.mapmaker.server;

import com.gwtplatform.dispatch.server.actionvalidator.ActionValidator;
import com.gwtplatform.dispatch.server.spring.HandlerModule;
import com.gwtplatform.dispatch.server.spring.actionvalidator.DefaultActionValidator;
import com.gwtplatform.dispatch.server.spring.configuration.DefaultModule;
import org.jason.mapmaker.server.actionHandler.*;
import org.jason.mapmaker.server.actionHandler.availableData.GetTigerLineVersionHandler;
import org.jason.mapmaker.server.actionHandler.availableData.GetUsgsFeaturesVersionHandler;
import org.jason.mapmaker.server.actionHandler.availableData.UpdateTigerLineVersionHandler;
import org.jason.mapmaker.server.actionHandler.availableData.UpdateUsgsFeaturesVersionHandler;
import org.jason.mapmaker.server.actionHandler.feature.DeleteSingleFeatureHandler;
import org.jason.mapmaker.server.actionHandler.feature.ImportSingleFeatureHandler;
import org.jason.mapmaker.server.actionHandler.featuresMetadata.CountFeaturesMetadataHandler;
import org.jason.mapmaker.server.actionHandler.featuresMetadata.GetFeaturesMetadataListHandler;
import org.jason.mapmaker.server.actionHandler.featuresMetadata.ImportFeaturesMetadataHandler;
import org.jason.mapmaker.server.actionHandler.location.GetCountiesByStateHandler;
import org.jason.mapmaker.server.actionHandler.location.GetLocationDescriptionsHandler;
import org.jason.mapmaker.server.actionHandler.location.GetLocationMapByCoordinatesHandler;
import org.jason.mapmaker.server.actionHandler.shapefile.DeleteSingleShapefileHandler;
import org.jason.mapmaker.server.actionHandler.shapefile.ImportSingleShapefileHandler;
import org.jason.mapmaker.server.actionHandler.shapefileMetadata.CountShapefileMetadataHandler;
import org.jason.mapmaker.server.actionHandler.shapefileMetadata.GetShapefileMetadataForGeoIdHandler;
import org.jason.mapmaker.server.actionHandler.shapefileMetadata.ImportShapefileMetadataHandler;
import org.jason.mapmaker.shared.action.*;
import org.jason.mapmaker.shared.action.availableData.GetTigerLineVersionAction;
import org.jason.mapmaker.shared.action.availableData.GetUsgsFeaturesVersionAction;
import org.jason.mapmaker.shared.action.availableData.UpdateTigerLineVersionAction;
import org.jason.mapmaker.shared.action.availableData.UpdateUsgsFeaturesVersionAction;
import org.jason.mapmaker.shared.action.feature.DeleteSingleFeatureAction;
import org.jason.mapmaker.shared.action.feature.ImportSingleFeatureAction;
import org.jason.mapmaker.shared.action.featuresMetadata.CountFeaturesMetadataAction;
import org.jason.mapmaker.shared.action.featuresMetadata.GetFeaturesMetadataListAction;
import org.jason.mapmaker.shared.action.featuresMetadata.ImportFeaturesMetadataAction;
import org.jason.mapmaker.shared.action.location.GetCountiesByStateAction;
import org.jason.mapmaker.shared.action.location.GetLocationDescriptionsAction;
import org.jason.mapmaker.shared.action.location.GetLocationMapByCoordinatesAction;
import org.jason.mapmaker.shared.action.shapefile.DeleteSingleShapefileAction;
import org.jason.mapmaker.shared.action.shapefile.ImportSingleShapefileAction;
import org.jason.mapmaker.shared.action.shapefileMetadata.CountShapefileMetadataAction;
import org.jason.mapmaker.shared.action.shapefileMetadata.GetShapefileMetadataForGeoIdAction;
import org.jason.mapmaker.shared.action.shapefileMetadata.ImportShapefileMetadataAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * ServerModule.java
 * <p/>
 * Spring module bindings, using the @Bean annotation for instantiating the Handler beans. Not my prefered way of
 * doing it, but it works.
 *
 * @author Jason Ferguson
 */
@Configuration
@Import(DefaultModule.class)
@SuppressWarnings("unused")
public class ServerModule extends HandlerModule {

    public ServerModule() {
    }


    // bind the actions to the actionhandler<A,R>
    protected void configureHandlers() {

        // data versions
        bindHandler(GetTigerLineVersionAction.class, GetTigerLineVersionHandler.class);
        bindHandler(GetUsgsFeaturesVersionAction.class, GetUsgsFeaturesVersionHandler.class);
        bindHandler(UpdateTigerLineVersionAction.class, UpdateTigerLineVersionHandler.class);
        bindHandler(UpdateUsgsFeaturesVersionAction.class, UpdateUsgsFeaturesVersionHandler.class);

        // featuresMetadata
        bindHandler(CountFeaturesMetadataAction.class, CountFeaturesMetadataHandler.class);
        bindHandler(GetFeaturesMetadataListAction.class, GetFeaturesMetadataListHandler.class);
        bindHandler(ImportFeaturesMetadataAction.class, ImportFeaturesMetadataHandler.class);

        // shapefileMetadata
        bindHandler(CountShapefileMetadataAction.class, CountShapefileMetadataHandler.class);
        bindHandler(ImportShapefileMetadataAction.class, ImportShapefileMetadataHandler.class);
        bindHandler(GetShapefileMetadataForGeoIdAction.class, GetShapefileMetadataForGeoIdHandler.class);

        // shapefile
        bindHandler(DeleteSingleShapefileAction.class, DeleteSingleShapefileHandler.class);
        bindHandler(ImportSingleShapefileAction.class, ImportSingleShapefileHandler.class);

        // feature
        bindHandler(DeleteSingleFeatureAction.class, DeleteSingleFeatureHandler.class);
        bindHandler(ImportSingleFeatureAction.class, ImportSingleFeatureHandler.class);

        // location
        bindHandler(GetCountiesByStateAction.class, GetCountiesByStateHandler.class);
        bindHandler(GetLocationDescriptionsAction.class, GetLocationDescriptionsHandler.class);
        bindHandler(GetLocationMapByCoordinatesAction.class, GetLocationMapByCoordinatesHandler.class);

        // TODO: Action cleanup... I know I'm not using all of these...
        bindHandler(GetAvailableLocationsCountAction.class, GetAvailableFeaturesCountHandler.class);
        bindHandler(GetCountyBasedLocationsAction.class, GetCountyBasedLocationsHandler.class);
        bindHandler(GetFeatureClassesAction.class, GetFeatureClassesHandler.class);
        bindHandler(GetLocationsAction.class, GetLocationsHandler.class);
        bindHandler(GetLocationsByStateAndMtfccAction.class, GetLocationsByStateAndMtfccHandler.class);
        bindHandler(GetMapDataByGeoIdAction.class, GetMapDataByGeoIdHandler.class);
        bindHandler(GetMtfccTypesAction.class, GetMtfccTypesHandler.class);
        bindHandler(GetStateBasedLocationsAction.class, GetStateBasedLocationsHandler.class);
        bindHandler(GetStatesByMtfccAction.class, GetStatesByMtfccHandler.class);
    }

    // return the handlers

    // available data
    @Bean
    public GetTigerLineVersionHandler getTigerLineVersionHandler() {
        return new GetTigerLineVersionHandler();
    }

    @Bean
    public GetUsgsFeaturesVersionHandler getUsgsFeaturesVersionHandler() {
        return new GetUsgsFeaturesVersionHandler();
    }

    @Bean
    public UpdateTigerLineVersionHandler getUpdateTigerLineVersionHandler() {
        return new UpdateTigerLineVersionHandler();
    }

    @Bean
    public UpdateUsgsFeaturesVersionHandler getUpdateUsgsFeaturesVersionHandler() {
        return new UpdateUsgsFeaturesVersionHandler();
    }

    // features metadata
    @Bean
    public CountFeaturesMetadataHandler getCountFeaturesMetadataHandler() {
        return new CountFeaturesMetadataHandler();
    }

    @Bean GetFeaturesMetadataListHandler getGetFeaturesMetadataListHandler() {
        return new GetFeaturesMetadataListHandler();
    }

    @Bean
    public ImportFeaturesMetadataHandler getImportFeaturesMetadataHandler() {
        return new ImportFeaturesMetadataHandler();
    }

    // shapefileMetadata
    @Bean
    public CountShapefileMetadataHandler getCountShapefileMetadataHandler() {
        return new CountShapefileMetadataHandler();
    }

    @Bean
    public GetShapefileMetadataForGeoIdHandler getGetShapefileMetadataForGeoIdHandler() {
        return new GetShapefileMetadataForGeoIdHandler();
    }

    @Bean
    public ImportShapefileMetadataHandler getImportShapefileMetadataHandler() {
        return new ImportShapefileMetadataHandler();
    }

    // shapefile
    @Bean
    public DeleteSingleShapefileHandler getDeleteSingleShapefileHandler() {
        return new DeleteSingleShapefileHandler();
    }

    @Bean
    public ImportSingleShapefileHandler getImportSingleShapefileHandler() {
        return new ImportSingleShapefileHandler();
    }

    // features
    @Bean
    public ImportSingleFeatureHandler getImportSingleFeatureHandler() {
        return new ImportSingleFeatureHandler();
    }

    @Bean
    public DeleteSingleFeatureHandler getDeleteSingleFeatureHandler() {
        return new DeleteSingleFeatureHandler();
    }

    @Bean
    public GetAvailableFeaturesCountHandler getGetAvailableFeaturesCountHandler() {
        return new GetAvailableFeaturesCountHandler();
    }

    @Bean
    public GetCountyBasedLocationsHandler getGetCountyBasedLocationsHandler() {
        return new GetCountyBasedLocationsHandler();
    }

    // location
    @Bean
    public GetCountiesByStateHandler getGetCountiesByStateHandler() {
        return new GetCountiesByStateHandler();
    }

    @Bean
    public GetLocationDescriptionsHandler getGetLocationDescriptionsHandler() {
        return new GetLocationDescriptionsHandler();
    }

    @Bean
    public GetLocationMapByCoordinatesHandler getGetLocationMapByCoordinatesHandler() {
        return new GetLocationMapByCoordinatesHandler();
    }

    @Bean
    public GetFeatureClassesHandler getGetFeatureClassesHandler() {
        return new GetFeatureClassesHandler();
    }

    @Bean
    public GetLocationsHandler getGetLocationsHandler() {
        return new GetLocationsHandler();
    }

    @Bean
    public GetLocationsByStateAndMtfccHandler getGetLocationsByStateAndMtfccHandler() {
        return new GetLocationsByStateAndMtfccHandler();
    }

    @Bean
    public GetMapDataByGeoIdHandler getGetMapDataByGeoIdHandler() {
        return new GetMapDataByGeoIdHandler();
    }

    @Bean
    public GetMtfccTypesHandler getGetMtfccTypesHandler() {
        return new GetMtfccTypesHandler();
    }

    @Bean
    public GetStateBasedLocationsHandler getGetStateBasedLocationsHandler() {
        return new GetStateBasedLocationsHandler();
    }

    @Bean
    public GetStatesByMtfccHandler getGetStatesByMtfccHandler() {
        return new GetStatesByMtfccHandler();
    }

    @Bean
    public ActionValidator getDefaultActionValidator() {
        return new DefaultActionValidator();
    }
}

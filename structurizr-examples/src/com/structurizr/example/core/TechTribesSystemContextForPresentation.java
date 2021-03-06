package com.structurizr.example.core;

import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;
import com.structurizr.io.json.JsonWriter;
import com.structurizr.model.*;
import com.structurizr.view.*;

import java.io.StringWriter;

/**
 * This is a model of the system context for the techtribes.je system,
 * the code for which can be found at https://github.com/techtribesje/techtribesje
 */
public class TechTribesSystemContextForPresentation {

    public static void main(String[] args) throws Exception {
        Workspace workspace = new Workspace("techtribes.je", "This is a model of the system context for the techtribes.je system, for use in presentations.");
        Model model = workspace.getModel();

        // create a model and the software system we want to describe
        SoftwareSystem techTribes = model.addSoftwareSystem(Location.Internal, "techtribes.je", "techtribes.je is the only way to keep up to date with the IT, tech and digital sector in Jersey and Guernsey, Channel Islands");

        // create the various types of people (roles) that use the software system
        Person anonymousUser = model.addPerson(Location.External, "Anonymous User", "Anybody on the web.");
        anonymousUser.uses(techTribes, "View people, tribes (businesses, communities and interest groups), content, events, jobs, etc from the local tech, digital and IT sector.");

        Person authenticatedUser = model.addPerson(Location.External, "Aggregated User", "A user or business with content aggregated into the website.");
        authenticatedUser.uses(techTribes, "Manage user profile and tribe membership.");

        Person adminUser = model.addPerson(Location.External, "Administration User", "A system administration user.");
        adminUser.uses(techTribes, "Add people, add tribes and manage tribe membership.");

        // create the various software systems that techtribes.je has a dependency on
        SoftwareSystem twitter = model.addSoftwareSystem(Location.External, "Twitter", "twitter.com");
        techTribes.uses(twitter, "Gets profile information and tweets from.");

        SoftwareSystem gitHub = model.addSoftwareSystem(Location.External, "GitHub", "github.com");
        techTribes.uses(gitHub, "Gets information about public code repositories from.");

        SoftwareSystem blogs = model.addSoftwareSystem(Location.External, "Blogs", "RSS and Atom feeds");
        techTribes.uses(blogs, "Gets content using RSS and Atom feeds from.");

        // now create the system context view based upon the model
        ViewSet viewSet = workspace.getViews();
        SystemContextView contextView = viewSet.createContextView(techTribes);
        contextView.addAllSoftwareSystems();
        contextView.addAllPeople();

        // tag and style some elements
        techTribes.addTags("techtribes.je");
        viewSet.getConfiguration().getStyles().add(new ElementStyle(Tags.ELEMENT, 600, 450, null, null, 40, Shape.RoundedBox));
        viewSet.getConfiguration().getStyles().add(new ElementStyle("techtribes.je", null, null, "#041F37", "#ffffff", null));
        viewSet.getConfiguration().getStyles().add(new ElementStyle(Tags.SOFTWARE_SYSTEM, null, null, "#2A4E6E", "#ffffff", null));
        viewSet.getConfiguration().getStyles().add(new ElementStyle(Tags.PERSON, 575, null, "#728da5", "white", 40, Shape.Person));
        viewSet.getConfiguration().getStyles().add(new RelationshipStyle(Tags.RELATIONSHIP, 5, null, null, 40, 500, null));
        contextView.setPaperSize(PaperSize.Slide_4_3);

        // and output the model and view to JSON
        JsonWriter jsonWriter = new JsonWriter(true);
        StringWriter stringWriter = new StringWriter();
        jsonWriter.write(workspace, stringWriter);
        System.out.println(stringWriter.toString());

        StructurizrClient structurizrClient = new StructurizrClient("https://api.structurizr.com", "key", "secret");
        structurizrClient.mergeWorkspace(101, workspace);
    }

}

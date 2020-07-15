/*
 * Copyright (C) 2016 Erik Jhordan Rey.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.erikjhordanrey.cleanarchitecture.data;

import io.github.erikjhordanrey.cleanarchitecture.data.entity.TeamEntity;
import io.github.erikjhordanrey.cleanarchitecture.fake.FakeTeamLocalAPI;
import io.github.erikjhordanrey.cleanarchitecture.data.local.LocalTeamApiToTeamEntityMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.Collection;
import java.util.Objects;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LocalTeamApiToTeamEntityMapperTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private LocalTeamApiToTeamEntityMapper localTeamApiToTeamEntityMapper;

    @Before
    public void setUp() {
        localTeamApiToTeamEntityMapper = new LocalTeamApiToTeamEntityMapper(new Gson());
    }

    @Test
    public void givenTransformCollectionTeamEntityCorrectly() {
        final String GIVEN_JSON_TEAM = FakeTeamLocalAPI.getJsonResponseTeamCollection();

        Collection<TeamEntity> teamEntities = localTeamApiToTeamEntityMapper.transformTeamEntityCollection(GIVEN_JSON_TEAM);

        assertThat(teamEntities.size(), is(3));
        assertEntityTeams(teamEntities);
    }

    @Test
    public void givenTransformTeamEntityCorrectly() {
        final String FAKE_JSON_RESPONSE_TEAM = FakeTeamLocalAPI.getJsonResponseTeam();

        TeamEntity teamEntity = localTeamApiToTeamEntityMapper.transformTeamEntity(FAKE_JSON_RESPONSE_TEAM);

        assertThat(teamEntity.getTeamFlag(), is("ALB"));
        assertThat(teamEntity.getTeamName(), is("Albania"));
        //you can try test each attribute is possible
    }

    @Test
    public void givenExpectedExceptionTransformUserEntityCollectionNotValidResponse() {
        expectedException.expect(JsonSyntaxException.class);

        localTeamApiToTeamEntityMapper.transformTeamEntityCollection("Expects a json array like response");
    }

    @Test
    public void givenExpectedExceptionTransformUserEntityNotValidResponse() {
        expectedException.expect(JsonSyntaxException.class);

        localTeamApiToTeamEntityMapper.transformTeamEntity("Expects a json object like response");
    }

    private void assertEntityTeams(Collection<TeamEntity> teamEntities) {
        final TeamEntity teamEntityOne = ((TeamEntity) Objects.requireNonNull(teamEntities.toArray())[0]);
        final TeamEntity teamEntityTwo = ((TeamEntity) Objects.requireNonNull(teamEntities.toArray())[1]);
        final TeamEntity teamEntityThree = ((TeamEntity) Objects.requireNonNull(teamEntities.toArray())[2]);
        assertThat(teamEntityOne.getTeamFlag(), is("ALB"));
        assertThat(teamEntityTwo.getTeamFlag(), is("AUT"));
        assertThat(teamEntityThree.getTeamFlag(), is("BEL"));
    }
}

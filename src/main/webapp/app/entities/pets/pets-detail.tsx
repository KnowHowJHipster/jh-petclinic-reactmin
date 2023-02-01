import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pets.reducer';

export const PetsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const petsEntity = useAppSelector(state => state.pets.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="petsDetailsHeading">Pets</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{petsEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{petsEntity.name}</dd>
          <dt>
            <span id="birthdate">Birthdate</span>
          </dt>
          <dd>{petsEntity.birthdate ? <TextFormat value={petsEntity.birthdate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>Type</dt>
          <dd>{petsEntity.type ? petsEntity.type.id : ''}</dd>
          <dt>Owner</dt>
          <dd>{petsEntity.owner ? petsEntity.owner.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/pets" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pets/${petsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PetsDetail;

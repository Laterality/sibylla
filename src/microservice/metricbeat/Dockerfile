FROM docker.elastic.co/beats/metricbeat:6.5.1
USER root
RUN groupadd docker --gid 999 && \
    usermod --append --group docker metricbeat
USER metricbeat